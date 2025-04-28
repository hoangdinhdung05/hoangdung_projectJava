package vn.hoangdung.projectJava.modules.users.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import vn.hoangdung.projectJava.modules.users.entities.RefreshToken;
import vn.hoangdung.projectJava.modules.users.repositories.RefreshTokenRepository;
import vn.hoangdung.projectJava.modules.users.requests.BlacklistedTokenRequest;
import vn.hoangdung.projectJava.modules.users.requests.LoginRequest;
import vn.hoangdung.projectJava.modules.users.requests.RefreshTokenRequest;
import vn.hoangdung.projectJava.modules.users.resources.LoginResources;
import vn.hoangdung.projectJava.modules.users.services.impl.BlacklistedTokenService;
import vn.hoangdung.projectJava.modules.users.services.interfaces.UserServiceInterface;
import vn.hoangdung.projectJava.resources.ErrorResource;
import vn.hoangdung.projectJava.resources.MessageResource;
import vn.hoangdung.projectJava.resources.RefreshTokenResource;
import vn.hoangdung.projectJava.services.JwtService;


@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final JwtService jwtService;
    // private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    private final UserServiceInterface userService;
    private final BlacklistedTokenService blacklistedTokenService;
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public AuthController(UserServiceInterface userService, BlacklistedTokenService blacklistedTokenService, JwtService jwtService) {
        this.blacklistedTokenService = blacklistedTokenService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {

        Object result = this.userService.authenticate(loginRequest);

        if(result instanceof LoginResources loginResources) {
            return ResponseEntity.ok(loginResources);
        }

        if(result instanceof ErrorResource errorResource) {
            return ResponseEntity.unprocessableEntity().body(errorResource);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Network error");
    }

    @PostMapping("/blacklisted_tokens")
    public ResponseEntity<?> addTokenToBlacklist(@RequestBody @Valid BlacklistedTokenRequest request) {

        try {

            Object result = this.blacklistedTokenService.create(request);
            return ResponseEntity.ok(result);

        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(new MessageResource("Netword Error !"));

        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) {
        try {

            String token = bearerToken.substring(7);
            //get info from token  
            BlacklistedTokenRequest request = new BlacklistedTokenRequest();
            request.setToken(token);

            Object result = blacklistedTokenService.create(request);
            return ResponseEntity.ok(result);


        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new MessageResource("Netword Error !"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        if(!jwtService.isRefreshTokenValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResource("RefreshToken không hợp lệ"));
        }

        Optional<RefreshToken> dbRefreshTokenOptional = this.refreshTokenRepository.findByRefreshToken(refreshToken);


        if(dbRefreshTokenOptional.isPresent()) {
            RefreshToken dBRefreshToken = dbRefreshTokenOptional.get();
            Long userId = dBRefreshToken.getUserId();
            String email = dBRefreshToken.getUser().getEmail();
    
            String newToken = jwtService.generateToken(userId, email);
            String newRefreshToken = jwtService.generateRefreshToken(userId, email);
            RefreshTokenResource refreshTokenResource = new RefreshTokenResource(newToken, newRefreshToken);
            
            return ResponseEntity.ok(refreshTokenResource);
        }


        return ResponseEntity.internalServerError().body(new MessageResource("Netword Error !"));
        
    }

}
