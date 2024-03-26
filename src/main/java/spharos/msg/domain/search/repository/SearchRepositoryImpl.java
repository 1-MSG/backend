package spharos.msg.domain.search.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.QProduct;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository {

    private static final int SEARCH_PRODUCT_SIZE = 10;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Product> searchAllProduct(String keyword, int index) {
        QProduct product = QProduct.product;
        return jpaQueryFactory
            .selectFrom(product)
            .where(
                product.productName.contains(keyword)
                    .or(product.productBrand.contains(keyword))
            )
            .distinct()
            .offset((long) index * SEARCH_PRODUCT_SIZE)
            .orderBy(product.id.desc())
            .limit(SEARCH_PRODUCT_SIZE)
            .fetch();
    }
}
