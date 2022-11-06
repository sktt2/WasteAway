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
                Optional<User> testuser2 = users.findByUsername("Shaker");
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
                                        "Film Type Camera");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/1%2F4eb7ecac-272d-44bf-bc6d-153304cde503?alt=media&token=11154ee6-c06b-4d10-bea3-7e35fa378faa");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("ADIDAS", "Well Used", LocalDateTime.now().toString(), "FASHION",
                                        "Heavily Used shoes, size 32 US");

                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/1%2F08a41caa-03e7-4653-a94c-f4d6e7127ff8?alt=media&token=bbdb601d-7f83-45f9-aaef-c0399d5876b7");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));

                        newProd = new Product("AIRPODS", "Heavily Used", LocalDateTime.now().toString(), "ELECTRONICS",
                                        "Broken pair of airpods");

                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/1%2F6bf585fe-3d1b-4621-9660-a31a5343ad16?alt=media&token=db45fdd0-8064-47de-9388-1b6048f21084");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("WII", "Heavily Used", LocalDateTime.now().toString(), "TOYS",
                                        "Heavily Used Wii console, does not on");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/1%2F79e0ee6f-b36e-4614-99b0-4a7ff52ff063?alt=media&token=fc83f7a9-6415-4ee4-bd41-a1ce915b81b7");
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
                                        "ELECTRONICS", "Works");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F203a9695-d69f-42fa-ba80-2f92c4a578bd?alt=media&token=d70384bb-a6a0-489c-88fc-b03ae7ea1d6e");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("IPHONE3", "Like New", LocalDateTime.now().toString(), "ELECTRONICS",
                                        "this is a description of the product");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("WATERBOTTLE", "Like New", LocalDateTime.now().toString(), "UTILITY",
                                        "this is a description of the product");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2Fff9415af-6fa5-4761-b9bf-5b25bb7909c7?alt=media&token=787ac4b5-a786-417f-baf1-38f60869feaf");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("AIRPODS", "Heavily Used", LocalDateTime.now().toString(), "ELECTRONICS",
                                        "this is a description of the product");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F6979f3ce-e830-4094-aea9-196d8be987fd?alt=media&token=460668bb-bbf7-4cbf-888c-1c478c4ec396");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("Micro-USB Wire", "Heavily Used", LocalDateTime.now().toString(),
                                        "ELECTRONICS",
                                        "Can charge devices but does not plug properly");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F368e0c1c-235f-49a1-96be-58bc1ca5c10d?alt=media&token=382aff96-6678-449d-bbfc-c12c7351e157");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));

                }

                if (!testuser2.isPresent()) {
                        User user = new User("Shaker", "drxwon@email.com", encoder.encode("password"));
                        Set<Role> role = new HashSet<>();
                        role.add(userRole);
                        role.add(adminRole);
                        user.setRoles(role);
                        System.out.println("[Add user]: " + users.save(user));
                        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), "SINGAPORE 138", 12345678);
                        userInfos.save(userInfo);

                        Product newProd = new Product("3-FAN TOWER", "Lightly Used", LocalDateTime.now().toString(),
                                        "ELECTRONICS",
                                        "Works fine");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/3%2Ff6580e8f-1732-483f-9037-b96d4f235396?alt=media&token=b85b68e6-0c63-4b0f-8336-2fd5f2e56d7d");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("NINTENDO SWITCH", "Like New", LocalDateTime.now().toString(), "TOYS",
                                        "Barely used");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/3%2F8407baa4-8ac0-4a86-8834-1f41e64554a3?alt=media&token=31cbcf5d-c4a0-45fc-b847-1d158bef9ede");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("SLIPPERS", "Well Used", LocalDateTime.now().toString(), "FASHION",
                                        "this is a description of the product");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/3%2F3ae68dc6-fe8e-4453-ba22-5004c4a29f45?alt=media&token=8aaffae7-2be2-4a5a-8d60-6f729523655f");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("COCACOLA GLASS", "Lightly Used", LocalDateTime.now().toString(),
                                        "OTHERS",
                                        "From Mcdonalds 2012 SG Youth Olympics");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/3%2F00c60a4d-cfb9-454f-8ecb-45eb890e41a0?alt=media&token=73fce09c-9593-4ccd-b26a-6eea040b4805");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                        newProd = new Product("CISCO BOOK", "Like New", LocalDateTime.now().toString(), "BOOKS",
                                        "Cisco Guide Book");
                        newProd.setImageUrl(
                                        "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/3%2Fd244bd7a-1fdb-451c-9738-948ae7b79686?alt=media&token=6183e399-64ec-4602-a07a-53aa19506e13");
                        newProd.setUser(user);
                        System.out.println("[Add product]:" + products.save(newProd));
                }
        }

}
