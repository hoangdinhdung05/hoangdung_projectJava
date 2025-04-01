package vn.hoangdung.projectJava.modules.users.resources;

public class LoginResources {
    
    private final String token;
    private final UserResources user;

    public LoginResources(String token, UserResources user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserResources getUser() {
        return user;
    }
}
