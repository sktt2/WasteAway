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
                        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), "SINGAPORE 511111",
                                        87231231);
                        userInfos.save(userInfo);
                        Product newProd = new Product("CAMERA", "Heavily Used", LocalDateTime.now().toString(),
                                        "ELECTRONICS",
                                        "this is a description of the product");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/1%2F4eb7ecac-272d-44bf-bc6d-153304cde503?alt=media&token=11154ee6-c06b-4d10-bea3-7e35fa378faa");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("ADIDAS", "Well Used", LocalDateTime.now().toString(), "FASHION",
                                        "this is a description of the product");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/1%2F08a41caa-03e7-4653-a94c-f4d6e7127ff8?alt=media&token=bbdb601d-7f83-45f9-aaef-c0399d5876b7");
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
                        Product newProd = new Product("LAPTOP", "Brand New", LocalDateTime.now().toString(),
                                        "ELECTRONICS",
                                        "this is a description of the product");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F203a9695-d69f-42fa-ba80-2f92c4a578bd?alt=media&token=d70384bb-a6a0-489c-88fc-b03ae7ea1d6e");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("IPHONE3", "Well Used", LocalDateTime.now().toString(), "ELECTRONICS",
                                        "this is a description of the product");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("WATERBOTTLE", "Brand New", LocalDateTime.now().toString(), "UTILITY",
                                        "this is a description of the product");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2Fff9415af-6fa5-4761-b9bf-5b25bb7909c7?alt=media&token=787ac4b5-a786-417f-baf1-38f60869feaf");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                }

        }

}
