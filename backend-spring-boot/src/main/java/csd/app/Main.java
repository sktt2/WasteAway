package csd.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import csd.app.user.User;
import csd.app.user.UserRepository;

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

    }

}
