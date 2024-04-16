package spharos.msg.domain.product.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.brand.entity.QBrand;
import spharos.msg.domain.product.dto.ProductResponse.ProductDeliveryDto;
import spharos.msg.domain.product.dto.ProductResponse.ProductIdDto;
import spharos.msg.domain.product.dto.QProductResponse_ProductDeliveryDto;
import spharos.msg.domain.product.dto.QProductResponse_ProductIdDto;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.QProduct;
import spharos.msg.domain.product.entity.QProductImage;
import spharos.msg.domain.product.entity.QProductSalesInfo;
import spharos.msg.domain.product.repository.ProductRepositoryCustom;

import java.util.List;

@Repository
@Slf4j
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
            .innerJoin(product.productSalesInfo,productSalesInfo).fetchJoin()
            .innerJoin(product.brand,brand).fetchJoin()
            .innerJoin(product.productImages,productImage).fetchJoin()
            .orderBy(productSalesInfo.productSellTotalCount.desc())
            .limit(11)
            .fetch();
    }

    @Override
    public List<Product> findProductsByIdListWithFetchJoin(List<Long> idList) {
        QProduct product = QProduct.product;
        QProductSalesInfo productSalesInfo = QProductSalesInfo.productSalesInfo;
        QBrand brand = QBrand.brand;
        QProductImage productImage = QProductImage.productImage;

        return jpaQueryFactory
            .selectFrom(product)
            .innerJoin(product.productSalesInfo,productSalesInfo).fetchJoin()
            .innerJoin(product.brand,brand).fetchJoin()
            .innerJoin(product.productImages,productImage).fetchJoin()
            .where(product.id.in(idList))
            .fetch();
    }

    @Override
    public ProductDeliveryDto findProductDelivery(Long productId) {
        QProduct product = QProduct.product;
        QBrand brand = QBrand.brand;

        return jpaQueryFactory
            .select(toDeliveryInfoDto(product,brand))
            .from(product)
            .innerJoin(product.brand,brand)
            .where(product.id.eq(productId))
            .fetchOne();
    }

    private QProductResponse_ProductDeliveryDto toDeliveryInfoDto(QProduct product,QBrand brand) {
        return new QProductResponse_ProductDeliveryDto(
            product.deliveryFee,
            brand.minDeliveryFee
        );
    }

    @Override
    public List<ProductIdDto> findRandomProductIds(Integer limit) {
        QProduct product = QProduct.product;
        return jpaQueryFactory
            .select(toProductIdDto(product))
            .from(product)
            .orderBy(Expressions.numberTemplate(Double.class,"RAND()").asc())
            .limit(limit)
            .fetch();
    }

    private QProductResponse_ProductIdDto toProductIdDto(QProduct product) {
        return new QProductResponse_ProductIdDto(
            product.id
        );
    }

    @Override
    public Page<ProductIdDto> findBestProducts(Pageable pageable,Long cursorTotalSellCount, Long cursorId) {
        QProduct product = QProduct.product;
        QProductSalesInfo productSalesInfo = QProductSalesInfo.productSalesInfo;

        List<ProductIdDto> content = jpaQueryFactory
            .select(toProductIdDto(product))
            .from(product)
            .join(product.productSalesInfo,productSalesInfo)
            .where(cursorTotalSellCountAndCursorId(cursorTotalSellCount,cursorId,product,productSalesInfo))
            .orderBy(productSalesInfo.productSellTotalCount.desc(),product.id.asc())
            .limit(pageable.getPageSize())
            .fetch();

        Long totalLong = jpaQueryFactory
            .select(product.count())
            .from(product)
            .fetchOne();

        // null 체크
        long total = totalLong != null ? totalLong : 0L;

     return new PageImpl<>(content,pageable,total);
    }

    private BooleanExpression cursorTotalSellCountAndCursorId(Long cursorTotalSellCount, Long cursorId, QProduct product,
        QProductSalesInfo productSalesInfo){
        if (cursorTotalSellCount == null || cursorId == null) {
            return null;
        }

        return productSalesInfo.productSellTotalCount.lt(cursorTotalSellCount)
            .or(productSalesInfo.productSellTotalCount.eq(cursorTotalSellCount)
            .and(product.id.gt(cursorId)));
    }
}
