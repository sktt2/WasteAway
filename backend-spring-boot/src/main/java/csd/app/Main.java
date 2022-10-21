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
            UserInfo userInfo = new UserInfo(user.getId(), "Test User", "User County Street 2", 87231231);
            userInfos.save(userInfo);
            Product newProd = new Product("Canon EOS 850D", "Near Mint", LocalDateTime.now().toString(), "Electronics",
                    "Only used once.");
            newProd.setImageUrl("https://cdn.mos.cms.futurecdn.net/8aicZoNgsmudp868vCQXym-320-80.jpg");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("Toddler Adidas Shoe Size 5", "Good", LocalDateTime.now().toString(), "Fashion",
                    "My child grew up.");
            newProd.setImageUrl("https://i.pinimg.com/736x/ed/70/b2/ed70b23f10a970b5f69fc692f5b1b34b.jpg");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("Apple AirPods", "Fair", LocalDateTime.now().toString(), "Electronics",
                    "I've bought new headsets so I don't really need this anymore.");
            newProd.setImageUrl(
                    "https://www.finder.com.au/finder-au/wp-uploads/2016/12/AppleAirPods_2_450.jpg?fit=900");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("Genki 1 Textbook", "Very Good", LocalDateTime.now().toString(), "Books",
                    "I'm at N2 right now so I won't be needing this anymore. No writings");
            newProd.setImageUrl(
                    "https://i.ebayimg.com/images/g/QmwAAOSwbLxf4Wo3/s-l400.jpg");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("1Q84 Trilogy by Haruki Murakami", "Excellent", LocalDateTime.now().toString(), "Books",
                    "I want more people to read books by Murakami");
            newProd.setImageUrl(
                    "https://i.pinimg.com/474x/b6/ad/07/b6ad07347eb6f462b47e664f0d5baf27--beautiful-beautiful.jpg");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("Pokemon Sword w/ Packaging", "Excellent", LocalDateTime.now().toString(), "Video Games",
                    "Was gifted this and enjoyed it. Hope someone will enjoy this game as well.");
            newProd.setImageUrl(
                    "https://i.ebayimg.com/images/g/Uk0AAOSwAE9gzMcY/s-l300.jpg");
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
            UserInfo userInfo = new UserInfo(user.getId(), "Test Admin", "TesterVille Street 1", 62626235);
            userInfos.save(userInfo);
            Product newProd = new Product("Asus VivoBook Pro 17", "Near Mint", LocalDateTime.now().toString(), "Electronics",
                "Only used once.");
            newProd.setImageUrl("https://www.lifewire.com/thmb/67vi64v6dYpE1QiEXeEbIir819Y=/400x0/filters:no_upscale():max_bytes(150000):strip_icc()/Asus_LaptopsUnder200_Vivobook_HeroSquare-1a4276e610624a81b8d9be9670f91028.jpg");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("iPhone 6", "Excellent", LocalDateTime.now().toString(), "Electronics",
                    "Used for 2 years. No scratches or damages.");
            newProd.setImageUrl("https://www.buyandsale.co/wp-content/uploads/classified-listing/2020/06/iPhone-6-plus-1-370x493.jpg");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("Stainless Steel Waterbottle", "Near-Mint", LocalDateTime.now().toString(), "Utility",
                    "Bought last year but only used once.");
            newProd.setImageUrl(
                    "https://i.pinimg.com/736x/26/e1/d4/26e1d43b6b65216c9239b45af9c3e9de.jpg");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
            newProd = new Product("Blue Among Us Plushie", "Mint", LocalDateTime.now().toString(), "Toys",
                    "Got it as a gift. Still clean, no dirt");
            newProd.setImageUrl(
                    "https://i.ebayimg.com/images/g/vL4AAOSw82hfwVBz/s-l400.png");
            newProd.setUser(user);
            System.out.println("[Add product]:" + products.save(newProd));
        }

    }

}
