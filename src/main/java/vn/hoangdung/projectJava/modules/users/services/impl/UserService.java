package vn.hoangdung.projectJava.modules.users.services.impl;

import org.springframework.stereotype.Service;

import vn.hoangdung.projectJava.modules.users.dto.LoginRequest;
import vn.hoangdung.projectJava.modules.users.dto.LoginResponse;
import vn.hoangdung.projectJava.modules.users.dto.UserDTO;
import vn.hoangdung.projectJava.modules.users.services.interfaces.UserServiceInterface;
import vn.hoangdung.projectJava.services.BaseService;

@Service
public class UserService extends BaseService implements UserServiceInterface {

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            
            // String email = loginRequest.getEmail();
            // String password = loginRequest.getPassword();
            String token = "1234";
            UserDTO user = new UserDTO(1L, "email@gmail.com");
            return new LoginResponse(token, user);

        } catch (Exception e) {
            throw new RuntimeException("Co van de xay ra");
        }
    }
    


}
