package vn.hoangdung.projectJava.modules.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoangdung.projectJava.modules.users.requests.LoginRequest;
import vn.hoangdung.projectJava.modules.users.resources.LoginResources;
import vn.hoangdung.projectJava.modules.users.services.interfaces.UserServiceInterface;


@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    private final UserServiceInterface userService;
    
    public AuthController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResources> login(@RequestBody @Valid LoginRequest loginRequest) {

        LoginResources auth = this.userService.login(loginRequest);

        return ResponseEntity.ok(auth);
    }

}
