package csd.app.payload.request;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirstTimeUserRequest {
    private String username;

    @NotNull
    private boolean firstTime;

    public String getFirstTime() {
        return Boolean.toString(firstTime);
    }

    public String username() {
        return username;
    }

    public FirstTimeUserRequest() {

    }

    public FirstTimeUserRequest(String username, boolean firstTime) {
        this.username = username;
        this.firstTime = firstTime;
    }


}
