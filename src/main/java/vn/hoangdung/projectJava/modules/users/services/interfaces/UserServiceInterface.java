package vn.hoangdung.projectJava.modules.users.services.interfaces;

import vn.hoangdung.projectJava.modules.users.requests.LoginRequest;


public interface UserServiceInterface {
    
    Object authenticate(LoginRequest loginRequest);

}
