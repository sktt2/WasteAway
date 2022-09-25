package csd.app.user;

import csd.app.roles.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // // @OneToOne(cascade = CascadeType.ALL)
    // // @JoinColumn(name = "userinfo_id", referencedColumnName = "id")
    // // private UserInfo userInfo;

    // /*
    // * Return a collection of authorities granted to the user.
    // */
    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    // return Arrays.asList(new SimpleGrantedAuthority(role));
    // }

    // /*
    // * The various is___Expired() methods return a boolean to indicate whether
    // * or not the user’s account is enabled or expired.
    // */
    // @Override
    // public boolean isAccountNonExpired() {
    // return true;
    // }

    // @Override
    // public boolean isAccountNonLocked() {
    // return true;
    // }

    // @Override
    // public boolean isCredentialsNonExpired() {
    // return true;
    // }

    // @Override
    // public boolean isEnabled() {
    // return true;
    // }
}