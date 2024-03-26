package spharos.msg.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.cart.entity.CartProduct;
import spharos.msg.domain.users.entity.Users;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByUsers(Users users);
}
