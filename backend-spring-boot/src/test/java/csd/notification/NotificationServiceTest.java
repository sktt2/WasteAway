// package csd.notification;

// import csd.app.chat.Chat;
// import csd.app.chat.ChatRepository;
// import csd.app.chat.ChatService;
// import csd.app.notification.Notification;
// import csd.app.notification.NotificationRepository;
// import csd.app.notification.NotificationService;
// import csd.app.notification.NotificationServiceImpl;
// import csd.app.product.Product;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import csd.app.product.ProductRepository;
// import csd.app.product.ProductServiceImpl;
// import csd.app.user.User;
// import csd.app.user.UserRepository;
// import csd.app.user.UserService;

// @ExtendWith(MockitoExtension.class)
// public class NotificationServiceTest {

// @Mock
// private ChatRepository chats;

// @Mock
// private NotificationRepository notifs;

// @InjectMocks
// private NotificationServiceImpl notificationService;

// @Mock
// private ChatService chatService;

// // test if addProduct gives product if product is valid
// @Test
// void addValidProduct_ReturnSavedProduct() {
// // arrange ***
// User user = new User("tester2", "blabla@hotmail.com",
// "password");
// Product product = new Product("yPhone 15 XL", "NEW",
// LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
// product.setUser(user);
// User user2 = new User("tester23", "blabla2@hotmail.com",
// "password");
// user.setId(1L);
// user2.setId(2L);
// Chat validChat = new Chat();
// validChat.setId(1L);
// validChat.setTaker(user2);
// validChat.setOwner(user);
// validChat.setProduct(product);

// Notification validnotif = new Notification(validChat, false);
// List<Notification> correctNotifList = new ArrayList<Notification>();
// correctNotifList.add(validnotif);
// // mock the "save" operation
// when(notifs.save(any(Notification.class))).thenReturn(validnotif);
// // act ***
// Notification savedNotif = notificationService.addNotification(validnotif);
// System.out.println(savedNotif);
// System.out.print(notifs.findAll());
// List<Notification> savedNotifList=
// notificationService.getNotificationbyUsername(user.getUsername());

// // assert ***
// assertEquals(correctNotifList, savedNotifList);
// }
// }