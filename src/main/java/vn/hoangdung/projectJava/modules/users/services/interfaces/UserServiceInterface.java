package vn.hoangdung.projectJava.modules.users.services.interfaces;

import vn.hoangdung.projectJava.modules.users.dto.LoginRequest;
import vn.hoangdung.projectJava.modules.users.dto.LoginResponse;

public interface UserServiceInterface {
    
    LoginResponse login(LoginRequest loginRequest);

}
