package vn.hoangdung.projectJava.modules.users.resources;

public class UserResources {
    
    private final long id;
    private final String email;
    private final String name;

    public UserResources(long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
