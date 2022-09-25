package csd.app.roles;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "roles")
public class Role {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
