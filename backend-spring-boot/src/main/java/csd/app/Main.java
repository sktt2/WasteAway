package csd.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import csd.app.user.User;
import csd.app.user.UserRepository;

import csd.app.product.Product;
import csd.app.product.ProductRepository;
import java.time.LocalDateTime;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Main.class, args);

        // JPA user repository init
        UserRepository users = ctx.getBean(UserRepository.class);
        BCryptPasswordEncoder encoder = ctx.getBean(BCryptPasswordEncoder.class);
        System.out.println("[Add user]: " + users.save(
                new User("admin", encoder.encode("goodpassword"), "ROLE_ADMIN")).getUsername());
        System.out.println("[Add user]: " + users.save(
                new User("abcde", encoder.encode("goodpassword"), "ROLE_USER")).getUsername());

        ProductRepository products = ctx.getBean(ProductRepository.class);
        System.out.println("[Add product]:"
                + products.save(new Product(000001, "ASUS LAPTOP", 87223344, "New", "SMU lvl 4", LocalDateTime.now())));

    }

}
