package vn.hoangdung.projectJava.modules.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoangdung.projectJava.modules.users.dto.LoginRequest;
import vn.hoangdung.projectJava.modules.users.dto.LoginResponse;
import vn.hoangdung.projectJava.modules.users.services.interfaces.UserServiceInterface;


@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserServiceInterface userService;
    
    public AuthController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse auth = this.userService.login(loginRequest);

        return ResponseEntity.ok(auth);
    }

}
