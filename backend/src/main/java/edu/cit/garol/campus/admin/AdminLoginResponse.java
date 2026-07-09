package edu.cit.garol.campus.auth;

public class AdminLoginResponse {

    private String id;
    private String firstName;
    private String role;
    private String redirectTo;

    public AdminLoginResponse(String id, String firstName, String role, String redirectTo) {
        this.id = id;
        this.firstName = firstName;
        this.role = role;
        this.redirectTo = redirectTo;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getRole() {
        return role;
    }

    public String getRedirectTo() {
        return redirectTo;
    }
}