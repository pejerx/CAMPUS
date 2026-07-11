package edu.cit.garol.campus.admin;

public class AdminLoginRequest {

    private String emailOrId;
    private String password;

    public AdminLoginRequest() {
    }

    public String getEmailOrId() {
        return emailOrId;
    }

    public void setEmailOrId(String emailOrId) {
        this.emailOrId = emailOrId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}