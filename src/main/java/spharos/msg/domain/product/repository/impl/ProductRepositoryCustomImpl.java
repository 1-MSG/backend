package spharos.msg.domain.product.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.brand.entity.QBrand;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.QProduct;
import spharos.msg.domain.product.entity.QProductImage;
import spharos.msg.domain.product.entity.QProductSalesInfo;
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

    @Override
    public List<Product> findBest11WithFetchJoin() {

        QProduct product = QProduct.product;
        QProductSalesInfo productSalesInfo = QProductSalesInfo.productSalesInfo;
        QBrand brand = QBrand.brand;
        QProductImage productImage = QProductImage.productImage;

        return jpaQueryFactory
            .selectFrom(product)
            .leftJoin(product.productSalesInfo,productSalesInfo).fetchJoin()
            .leftJoin(product.brand,brand).fetchJoin()
            .leftJoin(product.productImages,productImage).fetchJoin()
            .orderBy(productSalesInfo.productSellTotalCount.desc())
            .limit(11)
            .fetch();
    }
}
