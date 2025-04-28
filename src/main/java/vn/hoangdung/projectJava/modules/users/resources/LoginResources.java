package vn.hoangdung.projectJava.modules.users.resources;

public class LoginResources {
    
    private final String token;
    private final String refreshToken;
    private final UserResources user;

    public LoginResources(String token, String refreshToken, UserResources user) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserResources getUser() {
        return user;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
