package csd.app.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Entity
@Getter
@Setter
public class FirstTimeUser {
    @Id @GeneratedValue
    private Long firstTimeId;

    @Column(name = "firstTime", nullable = false) 
    private boolean firstTime = true;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "id")
    private User user;
    
    public FirstTimeUser() {

    }

    public FirstTimeUser(User user, boolean firstTime) {
        this.user = user;
        this.firstTime = firstTime;
    }
}
