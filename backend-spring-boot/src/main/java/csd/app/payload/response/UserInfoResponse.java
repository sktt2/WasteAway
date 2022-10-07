package csd.app.payload.response;

import java.util.List;

import csd.app.user.User;
import csd.app.user.UserInfo;

public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private UserInfo userinfo;

    public UserInfoResponse(Long id, String username, String email, List<String> roles, UserInfo userinfo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.userinfo = userinfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setUserInfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public UserInfo getUserInfo() {
        return userinfo;
    }
}
