package csd.app.chat;

import java.util.List;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import csd.app.notification.Notification;
import csd.app.product.*;
import csd.app.user.*;
import lombok.*;

@Entity
@Getter
public class Chat {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "taker_id", nullable = false)
    private User taker;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy="chat", cascade = CascadeType.ALL) 
    private List<Notification> notification;

    @Autowired
    public Chat(User taker, User owner, Product product) {
        this.taker = taker;
        this.owner = owner;
        this.product = product;
    }

    public Chat() {
    }

    public void setId(Long id) {
        this.id = id;
    }
}
