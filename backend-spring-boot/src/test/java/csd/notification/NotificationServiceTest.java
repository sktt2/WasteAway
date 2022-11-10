package csd.notification;

import csd.app.chat.Chat;
import csd.app.notification.Notification;
import csd.app.notification.NotificationRepository;
import csd.app.notification.NotificationServiceImpl;
import csd.app.product.Product;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import csd.app.user.User;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notifs;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    // test if addProduct gives product if product is valid
    @Test
    void addValidNotification_ReturnSavedNotification() {
    // arrange ***
    User user = new User("tester2", "blabla@hotmail.com",
    "password");
    Product product = new Product("yPhone 15 XL", "NEW",
    LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
    product.setUser(user);
    User user2 = new User("tester23", "blabla2@hotmail.com",
    "password");
    user.setId(1L);
    user2.setId(2L);
    Chat validChat = new Chat();
    validChat.setId(1L);
    validChat.setTaker(user2);
    validChat.setOwner(user);
    validChat.setProduct(product);

    Notification validnotif = new Notification(validChat, user2, user, false);
    // mock the "save" operation
    when(notifs.save(any(Notification.class))).thenReturn(validnotif);
    // act ***
    Notification savedNotif = notificationService.addNotification(validnotif);
    assertNotNull(savedNotif);
    }

    @Test
    void updateNotifIfRead_ReturnsNotifWithIsReadTrue() {
    // arrange ***
    User user = new User("tester2", "blabla@hotmail.com",
    "password");
    Product product = new Product("yPhone 15 XL", "NEW",
    LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
    product.setUser(user);
    User user2 = new User("tester23", "blabla2@hotmail.com",
    "password");
    user.setId(1L);
    user2.setId(2L);
    Chat validChat = new Chat();
    validChat.setId(1L);
    validChat.setTaker(user2);
    validChat.setOwner(user);
    validChat.setProduct(product);

    Notification validnotif = new Notification(validChat, user2, user, false);
    // mock the "save" operation
    when(notifs.save(any(Notification.class))).thenReturn(validnotif);
    // act ***
    Notification savedNotif = notificationService.addNotification(validnotif);
    Notification updatedNotif = notificationService.updateNotificationIfRead(savedNotif);
    assertTrue(updatedNotif.getIsRead());
    }
}