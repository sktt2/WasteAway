package csd.app;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import csd.app.user.User;
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
            Product newProd = new Product("CAMERA",  "OLD", "BLK1111122222", LocalDateTime.now());
            newProd.setUser(user);
            System.out.println("[Add product]:"+ products.save(newProd));
            Product newProd2 = new Product("ADIDAS",  "Used", "Plaza Singapura", LocalDateTime.now(), "this is a description of the product");
            newProd2.setUser(user);
            System.out.println("[Add product]:"+ products.save(newProd2));
            Product newProd4 = new Product("AIRPODS",  "Spoilt", "Coney Island", LocalDateTime.now(), "this is a description of the product");
            newProd4.setUser(user);
            System.out.println("[Add product]:"+ products.save(newProd4));
        }
        if (!testadmin.isPresent()) {
            User user = new User("testadmin", "testadmin@email.com", encoder.encode("password"));
            Set<Role> role = new HashSet<>();
            role.add(userRole);
            role.add(adminRole);
            user.setRoles(role);
            System.out.println("[Add user]: " + users.save(user));
            Product newProd = new Product("LAPTOP",  "New", "SMU lvl 4", LocalDateTime.now());
            newProd.setUser(user);
            System.out.println("[Add product]:"+ products.save(newProd));
            Product newProd3 = new Product("IPHONE3",  "Like-New", "NUS hall", LocalDateTime.now(),  "this is a description of the product");
            newProd3.setUser(user);
            System.out.println("[Add product]:"+ products.save(newProd3));
            Product newProd5 = new Product("WATERBOTTLE",  "New", "Tekong", LocalDateTime.now(),  "this is a description of the product");
            newProd5.setUser(user);
            System.out.println("[Add product]:"+ products.save(newProd5));
        }

    }

}
