package com.novelreader.demo.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.novelreader.demo.exception.AppException;
import com.novelreader.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    // Inject giá trị từ application.properties
    // Spring sẽ tìm key "application.security.jwt.secret-key"
    // và lấy giá trị tương ứng (đã được thay thế bởi biến môi trường từ .env)
    @NonFinal
    @Value(value = "${application.security.jwt.secret-key}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value(value = "${application.security.jwt.expiration}")
    protected long VALID_DURATION;

    /**
     * 1. Cỗ máy in vé (Tạo Token)
     */
    public String generateToken(UserDetails userDetails) {
        // A. Header: Thuật toán mã hóa HS512
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // B. Payload: Thông tin vé
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userDetails.getUsername())
                .issuer("novel-reader.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.MILLIS).toEpochMilli()
                ))
                .claim("scope", buildScope(userDetails))
                .build();

        // C. Signature: Ký tên
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 2. Máy quét vé (Verify Token)
     */
    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Kiểm tra chữ ký
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        boolean verified = signedJWT.verify(verifier);

        if (!verified) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Kiểm tra hạn sử dụng
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expirationTime.before(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    /**
     * 3. Helper: Trích xuất username (email) từ Token
     */
    public String extractUsername(String token) {
        try {
            SignedJWT signedJWT = verifyToken(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            // Nếu token lỗi, log warning và trả về null
            log.warn("Failed to extract username from token: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Helper: Build scope string
     */
    private String buildScope(UserDetails userDetails) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        userDetails.getAuthorities().forEach(authority ->
                stringJoiner.add(authority.getAuthority())
        );
        return stringJoiner.toString();
    }
}
