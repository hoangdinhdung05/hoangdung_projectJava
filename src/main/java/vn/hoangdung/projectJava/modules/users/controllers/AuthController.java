package vn.hoangdung.projectJava.modules.users.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import vn.hoangdung.projectJava.modules.users.requests.BlacklistedTokenRequest;
import vn.hoangdung.projectJava.modules.users.requests.LoginRequest;
import vn.hoangdung.projectJava.modules.users.resources.LoginResources;
import vn.hoangdung.projectJava.modules.users.services.impl.BlacklistedTokenService;
import vn.hoangdung.projectJava.modules.users.services.interfaces.UserServiceInterface;
import vn.hoangdung.projectJava.resources.ErrorResource;
import vn.hoangdung.projectJava.resources.MessageResource;


@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserServiceInterface userService;
    private final BlacklistedTokenService blacklistedTokenService;

    public AuthController(UserServiceInterface userService, BlacklistedTokenService blacklistedTokenService) {
        this.blacklistedTokenService = blacklistedTokenService;
        this.userService = userService;
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

}
