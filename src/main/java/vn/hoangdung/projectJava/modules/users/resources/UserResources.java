package vn.hoangdung.projectJava.modules.users.resources;

public class UserResources {
    
    private final long id;
    private final String email;

    public UserResources(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
