package csd.app.user;

import csd.app.product.Product;
import csd.app.roles.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

/*
 * Implementations of UserDetails to provide user information to Spring
 * Security,
 * e.g., what authorities (roles) are granted to the user and whether the
 * account is enabled or not
 */
public class User {
    // private static final long serialVersionUID = 1L;

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @NotNull(message = "Username should not be null")
    @Column(unique = true)
    private String username;

    // @NotNull(message = "name should not be null")
    // private String name;

    @Email
    @Column(unique = true)
    @NotNull(message = "email should not be null")
    private String email;

    // @NotNull(message = "address should not be null")
    // private String address;

    @NotNull(message = "Password should not be null")
    private String password;

    // @NotNull(message = "Status should not be null")
    // // Status TODO
    // private String status;

    // @NotNull(message = "phone number should not be null")
    // @Column(unique = true, length = 8)
    // private int phoneNumber;

    private String attempt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductInterest> productInterest = new HashSet<>();

    @OneToOne(mappedBy = "recommendation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Recommendation recommendation;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Product> products;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // // @OneToOne(cascade = CascadeType.ALL)
    // // @JoinColumn(name = "userinfo_id", referencedColumnName = "id")
    // // private UserInfo userInfo;

}