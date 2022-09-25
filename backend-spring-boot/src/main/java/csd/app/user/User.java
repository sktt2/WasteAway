package csd.app.user;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// import javax.persistence.CascadeType;
// import javax.persistence.JoinColumn;
// import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
// import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

/*
 * Implementations of UserDetails to provide user information to Spring
 * Security,
 * e.g., what authorities (roles) are granted to the user and whether the
 * account is enabled or not
 */
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @NotNull(message = "Username should not be null")
    @Column(unique = true)
    private String username;

    @NotNull(message = "name should not be null")
    private String name;

    @Email
    @Column(unique = true)
    @NotNull(message = "email should not be null")
    private String email;

    @NotNull(message = "address should not be null")
    private String address;

    @NotNull(message = "type should not be null")
    // We define two types: a for admins or u for users
    private String type;

    @NotNull(message = "Password should not be null")
    private String password;

    @NotNull(message = "Status should not be null")
    // Status TODO
    private String status;

    @NotNull(message = "email should not be null")
    @Column(unique = true, length = 8)
    private int phoneNumber;

    private String attempt;

    public User(String username, String name, String email, String address, int phoneNumber, String type,
            String password, String status) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.password = password;
        this.status = status;
    }

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "userinfo_id", referencedColumnName = "id")
    // private UserInfo userInfo;

    /*
     * Return a collection of authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(type));
    }

    /*
     * The various is___Expired() methods return a boolean to indicate whether
     * or not the user’s account is enabled or expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}