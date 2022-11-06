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
    private boolean firstTime;
    private UserInfo userInfo;

    public UserInfoResponse(Long id, String username, String email, List<String> roles, UserInfo userInfo, boolean firstTime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.firstTime = firstTime;
        this.userInfo = userInfo;
    }

    public UserInfoResponse(Long id, String username, String email, UserInfo userInfo, boolean firstTime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userInfo = userInfo;
        this.firstTime = firstTime;
    }
}
