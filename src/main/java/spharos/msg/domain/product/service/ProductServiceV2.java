package spharos.msg.domain.product.service;

import static spharos.msg.global.api.code.status.ErrorStatus.NOT_EXIST_PRODUCT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import spharos.msg.domain.category.entity.CategoryProduct;
import spharos.msg.domain.category.repository.CategoryProductRepository;
import spharos.msg.domain.product.converter.ProductConverter;
import spharos.msg.domain.product.dto.ProductResponse;
import spharos.msg.domain.product.dto.ProductResponse.ProductIdDto;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductImage;
import spharos.msg.domain.product.repository.ProductImageRepository;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.global.api.exception.ProductNotExistException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductServiceV2 {

    private final ProductRepository productRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final ProductImageRepository productImageRepository;

    //id로 상품의 기본 정보 불러오기
    public ProductResponse.ProductInfoDto getProductInfo(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotExistException(NOT_EXIST_PRODUCT));

        Integer discountPrice = getDiscountedPrice(product.getProductPrice(),
            product.getDiscountRate());

        return ProductConverter.toDto(product, discountPrice);
    }

    //id로 상품 썸네일 이미지 불러오기
    public ProductResponse.ProductImageDto getProductImage(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotExistException(NOT_EXIST_PRODUCT));

        ProductImage productImage = productImageRepository.findByProductAndImageIndex(product, 0)
            .orElseThrow(() -> new NotFoundException("해당 상품에 대한 index가 0인 이미지를 찾을 수 없음"));

        return ProductConverter.toDto(productImage);
    }

    //id로 상품 이미지들 불러오기
    public List<ProductResponse.ProductImageDto> getProductImages(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotExistException(NOT_EXIST_PRODUCT));

        List<ProductImage> productImages = productImageRepository.findByProduct(product);

        return productImages.stream().map(ProductConverter::toDto).toList();
    }

    //id로 상품 상세 html 불러오기
    public String getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotExistException(NOT_EXIST_PRODUCT));

        return product.getProductInfoDetail().getProductInfoDetailContent();
    }

    //id로 상품 상세 카테고리 정보 불러오기
    public ProductResponse.ProductCategoryDto getProductCategory(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotExistException(NOT_EXIST_PRODUCT));
        CategoryProduct categoryProduct = categoryProductRepository.findByProductWithFetchJoin(product);

        return ProductConverter.toDto(categoryProduct);
    }

    //id 리스트로 여러 상품 불러오기
    public List<ProductResponse.ProductInfoAdminDto> getProductsDetails(List<Long> idList) {
        List<Product> products = productRepository.findProductsByIdListWithFetchJoin(idList);
        return products.stream().map(ProductConverter::toAdminDto).toList();
    }

    //베스트 상품 목록 가져오기
    public ProductResponse.BestProductsDto getRankingProducts(Pageable pageable, Long cursorTotalSellCount, Long cursorId) {
        Page<ProductIdDto> bestProductsPage = productRepository.findBestProducts(
            pageable, cursorTotalSellCount, cursorId);
        Long totalProductCount = bestProductsPage.getTotalElements();
        Integer nowPage = pageable.getPageNumber();

        boolean isLast = !bestProductsPage.hasNext();
        log.info("getContent 확인"+bestProductsPage.getContent());
        return ProductConverter.toDto(totalProductCount, nowPage, bestProductsPage.getContent(), isLast);
    }

    //랜덤 상품 불러 오기
    public List<ProductResponse.ProductIdDto> getRandomProducts() {
        Integer RANDOM_LIMIT = 12;
        return productRepository.findRandomProductIds(RANDOM_LIMIT);
    }

    //어드민 베스트11 불러 오기
    public List<ProductResponse.Best11Dto> getBest11Products() {
        List<Product> products = productRepository.findBest11WithFetchJoin();

        return products.stream()
            .map(ProductConverter::toAdminBest11Dto)
            .toList();
    }

    //상품의 배송정보 불러 오기
    public ProductResponse.ProductDeliveryDto getProductDeliveryInfo(Long productId) {

        return productRepository.findProductDelivery(productId);
    }

    //할인가 계산하는 method
    private Integer getDiscountedPrice(Integer price, BigDecimal discountRate) {

        if (price == null || discountRate == null) {
            return price;
        }

        // BigDecimal로 원래 가격과 할인율을 계산
        BigDecimal normalPrice = BigDecimal.valueOf(price);
        BigDecimal discount = discountRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP); // 할인율을 백분율로 변환
        BigDecimal discountedPrice = normalPrice.multiply(BigDecimal.ONE.subtract(discount)); // 할인 가격 계산

        // 계산된 할인 가격을 정수로 변환하여 반환
        return discountedPrice.setScale(0, RoundingMode.HALF_UP).intValue();
    }

}