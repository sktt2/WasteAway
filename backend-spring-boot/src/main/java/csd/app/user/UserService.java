package csd.app.user;

import java.util.List;

public interface UserService {
    List<User> listUsers();
    User getUser(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User addUser(User user);
    UserInfo addUserInfo(UserInfo userInfo);
}
