package spharos.msg.domain.product.repository;

import java.util.List;
import spharos.msg.domain.product.dto.ProductResponse.ProductDeliveryDto;
import spharos.msg.domain.product.dto.ProductResponse.ProductIdDto;
import spharos.msg.domain.product.entity.Product;

public interface ProductRepositoryCustom {
    List<Product> findProductsByIdList(List<Long> idList);

    List<Product> findBest11WithFetchJoin();

    List<Product> findProductsByIdListWithFetchJoin(List<Long> idList);

    ProductDeliveryDto findProductDelivery(Long productId);

    List<ProductIdDto> findRandomProductIds(Integer limit);
}
