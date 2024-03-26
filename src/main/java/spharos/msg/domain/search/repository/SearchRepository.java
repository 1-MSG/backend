package spharos.msg.domain.search.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.product.entity.Product;

@Repository
public interface SearchRepository {

    List<Product> searchAllProduct(String keyword, int index);
}
