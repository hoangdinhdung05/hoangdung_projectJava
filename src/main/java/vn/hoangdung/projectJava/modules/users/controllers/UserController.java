package vn.hoangdung.projectJava.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoangdung.projectJava.modules.users.entities.User;
import vn.hoangdung.projectJava.modules.users.repositories.UserRepository;
import vn.hoangdung.projectJava.modules.users.resources.UserResources;
import vn.hoangdung.projectJava.resources.ApiResource;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        // logger.info("User email: " + email);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        UserResources userResources = UserResources.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .build();

        ApiResource<UserResources> response = ApiResource.ok(userResources, "Success");
        
        //Response sang SuccessResource
        // SuccessResource<UserResources> response = new SuccessResource<UserResources>("Success", userResources);

        return ResponseEntity.ok(response);

    }
    
}
