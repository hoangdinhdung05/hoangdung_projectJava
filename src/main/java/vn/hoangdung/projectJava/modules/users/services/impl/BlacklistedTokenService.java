package vn.hoangdung.projectJava.modules.users.services.impl;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.hoangdung.projectJava.modules.users.entities.BlacklistedToken;
import vn.hoangdung.projectJava.modules.users.repositories.BlacklistedTokenRepository;
import vn.hoangdung.projectJava.modules.users.requests.BlacklistedTokenRequest;
import vn.hoangdung.projectJava.resources.MessageResource;
import vn.hoangdung.projectJava.services.JwtService;

import java.time.ZoneId;
import java.util.Date;

@Service
public class BlacklistedTokenService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;
    @Autowired
    private JwtService jwtService;

    public Object create(BlacklistedTokenRequest request) {

        try {

            if(blacklistedTokenRepository.existsByToken(request.getToken())) {
//                Map<String, String> errors = new HashMap<>();
//                errors.put("message", "Token này đã có trong danh sách black list");
//                ErrorResource errorResource = new ErrorResource("Có vấn đề trong quá trình xác thực", errors);
//                return errorResource;
                return new MessageResource("Token đã tồn tại trong black list");
            }

            Claims claims = jwtService.getAllClaimsFromToken(request.getToken());

            Long userId = Long.valueOf(claims.getSubject());
            Date epiryDate = claims.getExpiration();

            BlacklistedToken blacklistedToken = new BlacklistedToken();
            blacklistedToken.setToken(request.getToken());
            blacklistedToken.setUserId(userId);
            blacklistedToken.setExpiryDate(epiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            blacklistedTokenRepository.save(blacklistedToken);

            logger.info("Thêm token vào black list");
            return new MessageResource("Thêm token vào black list");

        } catch (Exception e) {
            return new MessageResource("Error " + e.getMessage());
        }
    }

}
