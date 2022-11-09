package csd.app.user;
import csd.app.product.Product;
import csd.app.roles.*;
import csd.app.chat.*;
import csd.app.notification.Notification;

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

public class User {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @NotNull(message = "Username should not be null")
    @Column(unique = true)
    private String username;

    @Email
    @Column(unique = true)
    @NotNull(message = "email should not be null")
    private String email;

    @NotNull(message = "Password should not be null")
    private String password;

    private boolean firstTime = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "interested_user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "taker", cascade = CascadeType.ALL)
    private List<Chat> chatTakers;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Chat> chatOwners;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> messageSenders;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Message> messageReceivers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductInterest> productInterest = new HashSet<>();

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Product> products;
    
    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "sender", cascade= CascadeType.ALL)
    private List<Notification> notificationSenders;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "receiver", cascade= CascadeType.ALL)
    private List<Notification> notificationReceivers;
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}