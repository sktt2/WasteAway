package csd.app.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csd.app.notification.NotificationRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository users;

    @Autowired
    private UserInfoRepository userInfos;

    @Autowired
    private ProductInterestRepository productInterests;

    @Autowired NotificationRepository notifications;

    @Autowired
    private UserRecommendationRepository userRecommendations;

    public List<User> getUsers() {
        return users.findAll();
    }

    public User getUser(Long id) {
        if (users.existsById(id)) {
            return users.findById(id)
                    .orElseThrow(() -> new RuntimeException("Error: User not found."));
        }
        return null;
    }

    public User getUserByUsername(String username) {
        if (users.existsByUsername(username)) {
            return users.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Error: User not found."));
        }
        return null;
    }

    public User getUserByEmail(String email) {
        if (users.existsByEmail(email)) {
            return users.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Error: User not found."));
        }
        return null;
    }

    // Save user into database and return user with generated userId
    public User addUser(User user) {
        if (users.existsByUsername(user.getUsername()) || users.existsByEmail(user.getEmail())) {
            return null;
        }
        return users.save(user);
    }

    public UserInfo getUserInfoById(Long id) {
        return userInfos.getReferenceById(id);
    }

    public UserInfo addUserInfo(UserInfo userInfo) {
        if (userInfos.existsById(userInfo.getId())) {
            return null;
        }
        return userInfos.save(userInfo);
    }

    public ProductInterest getProductInterest(Long id) {
        if (productInterests.existsById(id)) {
            return productInterests.findById(id)
            .orElseThrow(() -> new RuntimeException("Error: Product Interest not found."));
        }
        return null;
    }
    public ProductInterest addProductInterest(ProductInterest prodinterest) {
        return productInterests.save(prodinterest);
    }

    public void deleteProductInterest(ProductInterest prodinterest) {
        productInterests.delete(prodinterest);
    };

    public List<ProductInterest> listProductInterests() {
        return productInterests.findAll();
    }

    public UserRecommendation getRecommendation(Long id) {
        if (userRecommendations.existsById(id)) {
            return userRecommendations.findById(id)
            .orElseThrow(() -> new RuntimeException("Error: Recommended category not found."));
        }
        return null;
    }

    public UserRecommendation updateRecommendation(UserRecommendation recommendation) {
        return userRecommendations.save(recommendation);
        
    }


    public User updateUser(User user) {
        return users.save(user);
    }
}
