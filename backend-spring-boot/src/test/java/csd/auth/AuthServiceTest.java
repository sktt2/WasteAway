package csd.auth;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import csd.app.user.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository users;

    @Mock
    private UserInfoRepository userInfos;

    @Mock
    PasswordEncoder encoder;
    
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUser_ValidId_ReturnUser() {
        User user = new User("tester1", "tester1@email.com", encoder.encode("password"));
        user.setId(1L);
        users.save(user);
        when(users.existsById(any(Long.class))).thenReturn(true);
        when(users.findById(any(Long.class))).thenReturn(Optional.of(user));

        User savedUser = userService.getUser(user.getId());

        assertNotNull(savedUser);
        assertEquals(user, savedUser);
        verify(users).existsById(user.getId());
        verify(users).findById(user.getId());
    }

    @Test
    void getUser_InvalidId_ReturnNull() {
        User user = new User("tester2", "tester2@email.com", encoder.encode("password"));
        user.setId(2L);
        users.save(user);
        when(users.existsById(any(Long.class))).thenReturn(false);

        User savedUser = userService.getUser(3L);

        assertNull(savedUser);
        verify(users).existsById(3L);
    }

    @Test
    void getUserByUsername_ValidUsername_ReturnUser() {
        User user = new User("tester3", "tester3@email.com", encoder.encode("password"));
        users.save(user);
        when(users.existsByUsername(any(String.class))).thenReturn(true);
        when(users.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        User savedUser = userService.getUserByUsername(user.getUsername());

        assertNotNull(savedUser);
        assertEquals(user, savedUser);
        verify(users).existsByUsername(user.getUsername());
        verify(users).findByUsername(user.getUsername());
    }

    @Test
    void getUserByUsername_InvalidUsername_ReturnNull() {
        User user = new User("tester4", "tester4@email.com", encoder.encode("password"));
        users.save(user);
        when(users.existsByUsername(any(String.class))).thenReturn(false);

        User savedUser = userService.getUserByUsername("tester5");

        assertNull(savedUser);
        verify(users).existsByUsername("tester5");
    }

    @Test
    void getUserByEmail_ValidEmail_ReturnUser() {
        User user = new User("tester5", "tester5@email.com", encoder.encode("password"));
        users.save(user);
        when(users.existsByEmail(any(String.class))).thenReturn(true);
        when(users.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        User savedUser = userService.getUserByEmail(user.getEmail());

        assertNotNull(savedUser);
        assertEquals(user, savedUser);
        verify(users).existsByEmail(user.getEmail());
        verify(users).findByEmail(user.getEmail());
    }

    @Test
    void getUserByEmail_InvalidEmail_ReturnNull() {
        User user = new User("tester6", "tester6@email.com", encoder.encode("password"));
        users.save(user);
        when(users.existsByEmail(any(String.class))).thenReturn(false);

        User savedUser = userService.getUserByEmail("tester7@email.com");

        assertNull(savedUser);
        verify(users).existsByEmail("tester7@email.com");
    }

    @Test
    void addUser_NewUser_ReturnSavedUser() {
        User user = new User("tester7", "tester7@email.com", encoder.encode("password"));
        when(users.existsByUsername(any(String.class))).thenReturn(false);
        when(users.existsByEmail(any(String.class))).thenReturn(false);
        when(users.save(any(User.class))).thenReturn(user);

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals(user, savedUser);
        verify(users).existsByUsername(user.getUsername());
        verify(users).existsByEmail(user.getEmail());
        verify(users).save(user);
    }

    @Test
    void addUser_ExistingUsername_ReturnNull() {
        User user = new User("tester8", "tester8@email.com", encoder.encode("password"));
        users.save(user);
        when(users.existsByUsername(any(String.class))).thenReturn(true);

        User savedUser = userService.addUser(user);

        assertNull(savedUser);
        verify(users).existsByUsername(user.getUsername());
    }

    @Test
    void addUser_ExistingEmail_ReturnNull() {
        User user = new User("tester9", "tester9@email.com", encoder.encode("password"));
        users.save(user);
        when(users.existsByUsername(any(String.class))).thenReturn(false);
        when(users.existsByEmail(any(String.class))).thenReturn(true);

        User savedUser = userService.addUser(user);

        assertNull(savedUser);
        verify(users).existsByUsername(user.getUsername());
        verify(users).existsByEmail(user.getEmail());
    }

    @Test
    void getUserInfoById_ValidUserInfo_ReturnUserInfo() {
        User user = new User("tester10", "tester10@email.com", encoder.encode("password"));
        user.setId(10L);
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), "Tester 10", "TesterVille Street 10", 81012101);
        userInfos.save(userInfo);
        when(userInfos.getReferenceById(any(Long.class))).thenReturn(userInfo);

        UserInfo savedUserInfo = userService.getUserInfoById(user.getId());

        assertNotNull(savedUserInfo);
        assertEquals(userInfo, savedUserInfo);
        verify(userInfos).getReferenceById(user.getId());
    }

    @Test
    void getUserInfoById_InvalidUserInfo_ThrowsEntityNotFoundException() {
        User user = new User("tester11", "tester11@email.com", encoder.encode("password"));
        user.setId(11L);
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), "Tester 11", "TesterVille Street 11", 81113111);
        userInfos.save(userInfo);
        when(userInfos.getReferenceById(any(Long.class))).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> userInfos.getReferenceById(9999L));
        verify(userInfos).getReferenceById(9999L);
    }

    @Test
    void addUserInfo_NewUserInfo_ReturnSavedUserInfo() {
        User user = new User("tester12", "tester12@email.com", encoder.encode("password"));
        user.setId(12L);
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), "Tester 12", "TesterVille Street 12", 81213121);
        when(userInfos.existsById(any(Long.class))).thenReturn(false);
        when(userInfos.save(any(UserInfo.class))).thenReturn(userInfo);

        UserInfo savedUserInfo = userService.addUserInfo(userInfo);

        assertNotNull(savedUserInfo);
        assertEquals(userInfo, savedUserInfo);
        verify(userInfos).existsById(userInfo.getId());
        verify(userInfos).save(userInfo);
    }

    @Test
    void addUserInfo_ExistingUserInfo_ReturnNull() {
        User user = new User("tester13", "tester13@email.com", encoder.encode("password"));
        user.setId(13L);
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), "Tester 13", "TesterVille Street 13", 81213121);
        when(userInfos.existsById(any(Long.class))).thenReturn(true);

        UserInfo savedUserInfo = userService.addUserInfo(userInfo);

        assertNull(savedUserInfo);
        verify(userInfos).existsById(userInfo.getId());
    }
}
