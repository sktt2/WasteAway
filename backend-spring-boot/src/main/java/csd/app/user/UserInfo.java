package csd.app.user;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.*;

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
public class UserInfo implements Serializable{
    // private static final long serialVersionUID = 1L;
    private @Id Long id;
    
    @NotNull(message = "Please enter your name")
    @Column
    private String name;

    @Column
    private String address;

    // @NotNull(message = "address should not be null")
    // private String address;

    @Column(length = 8)
    private int phoneNumber;

    // @NotNull(message = "phone number should not be null")
    // @Column(unique = true, length = 8)
    // private int phoneNumber;

    @OneToOne
    @JoinColumn(name = "id")
    private User user;


    public UserInfo(Long id, String name, String address, int phoneNumber) {
        this.id = id;
        this.name =name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // // @OneToOne(cascade = CascadeType.ALL)
    // // @JoinColumn(name = "userinfo_id", referencedColumnName = "id")
    // // private UserInfo userInfo;

}