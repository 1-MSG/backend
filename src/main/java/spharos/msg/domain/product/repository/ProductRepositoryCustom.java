package spharos.msg.domain.product.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.product.dto.ProductResponse.ProductDeliveryDto;
import spharos.msg.domain.product.dto.ProductResponse.ProductIdDto;
import spharos.msg.domain.product.entity.Product;

public interface ProductRepositoryCustom {
    List<Product> findProductsByIdList(List<Long> idList);

    List<Product> findBest11WithFetchJoin();

    List<Product> findProductsByIdListWithFetchJoin(List<Long> idList);

    ProductDeliveryDto findProductDelivery(Long productId);

    List<ProductIdDto> findRandomProductIds(Integer limit);

    Page<ProductIdDto> findBestProducts(Pageable pageable,Long cursorTotalSellCount, Long cursor);
}
