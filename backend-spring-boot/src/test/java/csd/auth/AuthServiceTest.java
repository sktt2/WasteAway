package csd.auth;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

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
}
