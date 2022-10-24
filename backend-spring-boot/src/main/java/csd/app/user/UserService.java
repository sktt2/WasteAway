package csd.app.user;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(Long id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User addUser(User user);

    UserInfo getUserInfoById(Long id);

    UserInfo addUserInfo(UserInfo userInfo);

    User updateUser(User user);
}
