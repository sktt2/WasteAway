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
    ProductInterest getProductInterest(Long id);
    ProductInterest addProductInterest(ProductInterest prodinterest);
    void deleteProductInterest(ProductInterest prodinterest);
    List<ProductInterest> listProductInterests();

    Recommendation getRecommendation(Long id);
    Recommendation addRecommendation(Recommendation recommendation);
    Recommendation updateRecommendation(Recommendation recommendation);
    List<Recommendation> listRecommendations();

    User updateUser(User user);
}
