package vn.hoangdung.projectJava.modules.users.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.hoangdung.projectJava.modules.users.entities.User;
import vn.hoangdung.projectJava.modules.users.repositories.UserRepository;
import vn.hoangdung.projectJava.modules.users.requests.LoginRequest;
import vn.hoangdung.projectJava.modules.users.resources.LoginResources;
import vn.hoangdung.projectJava.modules.users.resources.UserResources;
import vn.hoangdung.projectJava.modules.users.services.interfaces.UserServiceInterface;
import vn.hoangdung.projectJava.resources.ErrorResource;
import vn.hoangdung.projectJava.services.BaseService;
import vn.hoangdung.projectJava.services.JwtService;

@Service
public class UserService extends BaseService implements UserServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object authenticate(LoginRequest request) {
        try {
            
            // String email = request.getEmail();
            // String password = request.getPassword();


            //Lấy thông tin User
            User user = this.userRepository.findByEmail(request.getEmail()).orElseThrow(() -> 
            new BadCredentialsException("Email hoặc mật khẩu không chính xác"));

            if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Email hoặc mật khẩu không chính xác");
            }

            UserResources userResources = new UserResources(user.getId(), user.getEmail(), user.getName(), user.getPhone());
            String token = jwtService.generateToken(user.getId(), user.getEmail());
            return new LoginResources(token, userResources);

        } catch (BadCredentialsException e) {
            logger.error("Lỗi xác thực : {}", e.getMessage());
            Map<String, String> errors = new HashMap<>();

            errors.put("message", e.getMessage());
            ErrorResource errorResource = new ErrorResource("Lỗi xác thức", errors);
            return errorResource;

        }
    }
    


}
