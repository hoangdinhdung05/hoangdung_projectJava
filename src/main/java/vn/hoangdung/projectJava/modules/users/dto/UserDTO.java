package vn.hoangdung.projectJava.modules.users.dto;

public class UserDTO {
    
    private final long id;
    private final String email;

    public UserDTO(long id, String email) {
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
