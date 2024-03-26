package spharos.msg.domain.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.likes.entity.Likes;
import spharos.msg.domain.users.entity.Users;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean existsByUsersAndProduct(Users users, Product product);
    Optional<Likes> findByUsersAndProduct(Users users, Product product);
    List<Likes> findByUsers(Users users);
}