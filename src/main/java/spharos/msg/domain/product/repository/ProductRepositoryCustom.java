package spharos.msg.domain.product.repository;

import java.util.List;
import spharos.msg.domain.product.entity.Product;

public interface ProductRepositoryCustom {
    List<Product> findProductsByIdList(List<Long> idList);

    List<Product> findBest11WithFetchJoin();
}
