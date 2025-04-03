package vn.hoangdung.projectJava.services;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.stereotype.Service;
import vn.hoangdung.projectJava.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JwtService.class);
    private final JwtConfig jwtConfig;
    private final Key key;

    public JwtService(JwtConfig jwtConfig, Key key) {
        this.jwtConfig = jwtConfig;
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes()));
    }

    public String generateToken(long userId, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpirationTime());

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .setIssuedAt(now)
                .setIssuer(jwtConfig.getIssuer())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //Get ID
    // public String extractUsername(String token) {
    //     return extractClaim(token, Claims::getSubject);
    // }

    private <T> T getClaimFromToken(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // //giải mã và lấy ra claims
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Lấy userId từ token
    public String getUserIdFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getEmailFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("email", String.class);
    }

    //Kiểm tra token có hợp lệ không
    
    /*
     * 1. Check token có đúng định dạng không
     * 2. Check token có hết hạn không
     * 3. Check secret có đúng không
     * 4. Check userId có tồn tại không (có matches với userId trong DB không)
     * 5. Check token có trong blacklist không
     */

    // public boolean isValidToken(String token, UserDetails userDetails) {
    //     try {
    //         //1. Check định dạng token
    //         if(!isTokenFormatValid(token)) {
    //             logger.info("Token không đúng định dạng");
    //             return false;
    //         }
    //         //2. Check secret có đúng không
    //         if(!isSignatureValid(token)) {
    //             logger.info("Token không đúng secret");
    //             return false;
    //         }
    //         //3. Check token có hết hạn không
    //         if(!isTokenExpired(token)) {
    //             logger.info("Token đã hết hạn");
    //             return false;
    //         }
    //         //4. Check nguồn gốc
    //         if(!isIssuerToken(token)) {
    //             logger.info("Token không đúng nguồn gốc");
    //             return false;
    //         }
    //         //5. Check username có tồn tại không
    //         final String emailFromToken = getEmailFromJwt(token);
    //         if(!emailFromToken.equals(userDetails.getUsername())) {
    //             logger.info("UserToken không hợp lệ");
    //             return false;
    //         }
    //         //6. Check blackListToken
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //         logger.info("Xác thực token thất bại" + e.getMessage());
    //         return false;
    //     }
    //     return false;
    // }

    //Check định dạng
    public boolean isTokenFormatValid(String token) {
        try {
            //Có 3 phần tử, mỗi phần tử cách nhau bởi dấu "."
            //Cắt chuỗi thành 3 phần tử
            String[] parts = token.split("\\.");
            return parts.length == 3;
            
        } catch (Exception e) {
            // TODO: handle exception
            // logger.info("Token không hợp lệ" + e.getMessage());
            return false;
        }
    }

    //Check secret
    public boolean isSignatureValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    //Get secret
    public Key getSecretKey() {
        byte[] keyBytes = this.jwtConfig.getSecretKey().getBytes();
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(keyBytes));
    }

    //Check expired
    public boolean isTokenExpired(String token) {
        try {
            final Date expiration = getClaimFromToken(token, Claims::getExpiration);
            return expiration.before(new Date());
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public boolean isIssuerToken(String token) {
        String tokenIssuer = getClaimFromToken(token, Claims::getIssuer);
        return this.jwtConfig.getIssuer().equals(tokenIssuer);
    }

}
