package csd.auth;

import static org.junit.Assert.assertThrows;
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
    void addUser_ExistingUsername_ReturnNull() {
        User user = new User("tester1", "tester1@email.com", encoder.encode("password"));
        when(users.existsByUsername(user.getUsername())).thenReturn(true);

        User savedUser = userService.addUser(user);

        assertNull(savedUser);
        verify(users).existsByUsername(user.getUsername());
    }

    @Test
    void addUser_ExistingEmail_ReturnNull() {
        User user = new User("tester1", "tester1@email.com", encoder.encode("password"));
        when(users.existsByUsername(user.getUsername())).thenReturn(false);
        when(users.existsByEmail(user.getEmail())).thenReturn(true);

        User savedUser = userService.addUser(user);

        assertNull(savedUser);
        verify(users).existsByUsername(user.getUsername());
        verify(users).existsByEmail(user.getEmail());
    }
}
