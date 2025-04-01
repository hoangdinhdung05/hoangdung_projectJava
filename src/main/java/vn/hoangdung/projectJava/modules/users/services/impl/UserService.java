package vn.hoangdung.projectJava.modules.users.services.impl;

import org.springframework.stereotype.Service;

import vn.hoangdung.projectJava.modules.users.requests.LoginRequest;
import vn.hoangdung.projectJava.modules.users.resources.LoginResources;
import vn.hoangdung.projectJava.modules.users.resources.UserResources;
import vn.hoangdung.projectJava.modules.users.services.interfaces.UserServiceInterface;
import vn.hoangdung.projectJava.services.BaseService;

@Service
public class UserService extends BaseService implements UserServiceInterface {

    @Override
    public LoginResources login(LoginRequest loginRequest) {
        try {
            
            // String email = loginRequest.getEmail();
            // String password = loginRequest.getPassword();
            String token = "1234";
            UserResources user = new UserResources(1L, "email@gmail.com");
            return new LoginResources(token, user);

        } catch (Exception e) {
            throw new RuntimeException("Co van de xay ra");
        }
    }
    


}
