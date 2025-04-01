package vn.hoangdung.projectJava.modules.users.services.interfaces;

import vn.hoangdung.projectJava.modules.users.requests.LoginRequest;
import vn.hoangdung.projectJava.modules.users.resources.LoginResources;

public interface UserServiceInterface {
    
    Object authenticate(LoginRequest loginRequest);

}
