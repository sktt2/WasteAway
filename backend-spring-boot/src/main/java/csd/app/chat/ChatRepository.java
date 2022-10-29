package csd.app.chat;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import csd.app.product.*;
import csd.app.user.*;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findByProductAndTaker(Product product, User taker);
    List<Chat> findByOwner(User owner);
    List<Chat> findByTaker(User owner);
}
