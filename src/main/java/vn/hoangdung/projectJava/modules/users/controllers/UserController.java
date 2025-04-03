package vn.hoangdung.projectJava.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoangdung.projectJava.modules.users.enities.User;
import vn.hoangdung.projectJava.modules.users.repositories.UserRepository;
import vn.hoangdung.projectJava.modules.users.resources.UserResources;
import vn.hoangdung.projectJava.resources.SuccessResource;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        String email = "hoangdinhdung0205@gmail.com";

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        UserResources userResources = new UserResources(user.getId(), user.getEmail(), user.getName());

        //Response sang SuccessResource
        SuccessResource<UserResources> response = new SuccessResource<UserResources>("Success", userResources);

        return ResponseEntity.ok(response);

    }
    
}
