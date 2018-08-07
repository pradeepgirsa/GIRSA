package za.co.global.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
