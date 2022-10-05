package csd.app;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import csd.app.user.User;
import csd.app.user.UserInfo;
import csd.app.user.UserInfoRepository;
import csd.app.user.UserRepository;
import csd.app.roles.*;

import csd.app.product.Product;
import csd.app.product.ProductRepository;
import java.time.LocalDateTime;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        // SpringApplication.run(Main.class, args);
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        // Setup Roles table
        RoleRepository roles = ctx.getBean(RoleRepository.class);
        Optional<Role> roleAdmin = roles.findByName(ERole.ROLE_ADMIN);
        Optional<Role> roleUser = roles.findByName(ERole.ROLE_USER);
        if (!roleAdmin.isPresent()) {
            roles.save(new Role(ERole.ROLE_ADMIN));
        }
        if (!roleUser.isPresent()) {
            roles.save(new Role(ERole.ROLE_USER));
        }

        // JPA user repository init
        UserRepository users = ctx.getBean(UserRepository.class);
        ProductRepository products = ctx.getBean(ProductRepository.class);
        UserInfoRepository userInfos = ctx.getBean(UserInfoRepository.class);
        BCryptPasswordEncoder encoder = ctx.getBean(BCryptPasswordEncoder.class);
        Optional<User> testuser = users.findByUsername("testuser");
        Optional<User> testadmin = users.findByUsername("testadmin");
        Role adminRole = roles.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Role userRole = roles.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        if (!testuser.isPresent()) {
            User user = new User("testuser", "testuser@email.com", encoder.encode("password"));
            Set<Role> role = new HashSet<>();
            role.add(userRole);
            user.setRoles(role);
            System.out.println("[Add user]: " + users.save(user));
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), "SINGAPORE 511111", 87231231);
            userInfos.save(userInfo);
            Product newProd = new Product("CAMERA", "OLD", LocalDateTime.now().toString(), "ELECTRONICS");
            newProd.setImageUrl("https://c.tenor.com/Ru4rM3mFd-0AAAAj/dino-dinosaur.gif");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("ADIDAS", "Used", LocalDateTime.now().toString(), "FASHION",
                    "this is a description of the product");
            newProd.setImageUrl("https://c.tenor.com/Ru4rM3mFd-0AAAAj/dino-dinosaur.gif");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
        }
        if (!testadmin.isPresent()) {
            User user = new User("testadmin", "testadmin@email.com", encoder.encode("password"));
            Set<Role> role = new HashSet<>();
            role.add(userRole);
            role.add(adminRole);
            user.setRoles(role);
            System.out.println("[Add user]: " + users.save(user));
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), "SINGAPORE 111", 12345678);
            userInfos.save(userInfo);
            Product newProd = new Product("LAPTOP", "New", LocalDateTime.now().toString(), "ELECTRONICS");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("IPHONE3", "Like-New", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl("https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-3gs-ofic.jpg");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("WATERBOTTLE", "New", LocalDateTime.now().toString(), "UTILITY",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/HQ222?wid=1649&hei=2207&fmt=jpeg&qlt=95&.v=1654034080576");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("AIRPODS", "Spoilt", LocalDateTime.now().toString(), "ELECTRONICS",
                    "this is a description of the product");
            newProd.setImageUrl(
                    "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MV7N2?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1551489688005");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));

        }

    }

}
