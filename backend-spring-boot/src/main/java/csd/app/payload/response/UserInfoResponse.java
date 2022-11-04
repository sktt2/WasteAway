package csd.app.payload.response;

import java.util.List;
import csd.app.user.UserInfo;
import lombok.*;

@Getter
@Setter

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

    public UserInfoResponse(Long id, String username, String email, UserInfo userinfo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userinfo = userinfo;
    }
}
