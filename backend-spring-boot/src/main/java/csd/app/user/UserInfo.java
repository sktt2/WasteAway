package csd.app.user;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class UserInfo implements Serializable {

    @JsonIgnore
    private @Id Long id;

    @NotBlank(message = "Please enter your name")
    @Column
    private String name;

    @Column
    private String address;

    @Column(length = 8)
    private int phoneNumber;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "id")
    private User user;

    public UserInfo(Long id, String name, String address, int phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

}