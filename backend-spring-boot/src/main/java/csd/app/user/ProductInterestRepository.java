package csd.app.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInterestRepository extends JpaRepository<ProductInterest, Long>{
    
}
