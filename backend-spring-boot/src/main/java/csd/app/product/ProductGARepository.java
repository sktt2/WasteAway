package csd.app.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGARepository extends JpaRepository<ProductGA, Long> {
    
}