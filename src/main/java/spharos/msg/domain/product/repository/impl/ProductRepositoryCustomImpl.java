package spharos.msg.domain.product.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.QProduct;
import spharos.msg.domain.product.repository.ProductRepositoryCustom;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Product> findProductsByIdList(List<Long> idList) {
        // Product 엔티티에 대한 Q 클래스 생성
        QProduct product = QProduct.product;

        return jpaQueryFactory
            .selectFrom(product)
            .where(product.id.in(idList))
            .fetch();
    }
}
