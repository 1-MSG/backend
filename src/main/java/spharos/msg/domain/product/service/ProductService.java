package spharos.msg.domain.product.service;

import static spharos.msg.global.api.code.status.ErrorStatus.NOT_EXIST_PRODUCT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import spharos.msg.domain.category.entity.CategoryProduct;
import spharos.msg.domain.category.repository.CategoryProductRepository;
import spharos.msg.domain.orders.repository.OrderProductRepository;
import spharos.msg.domain.product.converter.ProductConverter;
import spharos.msg.domain.product.dto.ProductResponse;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductImage;
import spharos.msg.domain.product.repository.ProductImageRepository;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.domain.product.repository.ProductRepositoryCustom;
import spharos.msg.global.api.exception.ProductNotExistException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final ProductImageRepository productImageRepository;
    private final OrderProductRepository orderProductRepository;

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
        CategoryProduct categoryProduct = categoryProductRepository.findByProduct(product);

        return ProductConverter.toDto(categoryProduct);
    }

    //id리스트로 여러 상품 불러오기
    public List<ProductResponse.ProductInfoDto> getProductsDetails(List<Long> idList) {
        List<Product> products = productRepository.findProductsByIdList(idList);
        return products.stream().map(product -> {
            ProductImage productImage = productImageRepository.findByProductAndImageIndex(product,
                    0)
                .orElseGet(ProductImage::new); // 이미지를 찾을 수 없을 때 빈 ProductImage 객체를 생성하여 반환

            Integer discountPrice = getDiscountedPrice(product.getProductPrice(), product.getDiscountRate());

            return ProductConverter.toDto(product,productImage,discountPrice);
        }).toList();
    }

    //베스트 상품 목록 가져오기
    public ProductResponse.BestProductsDto getRankingProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllByOrderByProductSalesInfoProductSellTotalCountDesc(
            pageable);

        boolean isLast = !productPage.hasNext();

        List<ProductResponse.ProductIdDto> productList = productPage.getContent().stream().map(
                ProductConverter::toDto)
            .toList();

        return ProductConverter.toDto(productList, isLast);
    }

    //랜덤 상품 불러 오기
    public List<ProductResponse.ProductIdDto> getRandomProducts() {
        //최근 1달 주문 내역의 productId 불러 오기
        List<Long> recentProducts = orderProductRepository.findProductIdsCreatedLastMonth();
        // 주문 내역이 없을 경우
        if (recentProducts.isEmpty()) {
            return productRepository.findRandomProducts(12).stream()
                .map(ProductConverter::toDto).toList();
        }
        // 주문 내역이 있을 경우
        return getRandomProductsByInterestedCategory(recentProducts);
    }

    //어드민 베스트11 불러 오기
    public List<ProductResponse.Best11Dto> getBest11Products() {
        List<Product> products = productRepository.findTop11ByOrderByProductSalesInfoProductSellTotalCountDesc();

        return products.stream()
            .map(product -> {
                ProductImage productImage = productImageRepository.findByProductAndImageIndex(product, 0)
                    .orElse(new ProductImage());

                return ProductConverter.toDto(product,productImage);
            })
            .toList();
    }

    //상품의 배송정보 불러 오기
    public ProductResponse.ProductDeliveryDto getProductDeliveryInfo(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotExistException(NOT_EXIST_PRODUCT));

        return ProductResponse.ProductDeliveryDto.builder()
            .deliveryFee(product.getDeliveryFee())
            .minDeliveryFee(product.getBrand().getMinDeliveryFee())
            .build();
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

    private Long getKeyWithMaxValue(Map<Long, Integer> map) {
        Long maxKey = null;
        Integer maxValue = Integer.MIN_VALUE;

        for (Map.Entry<Long, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }

        return maxKey;
    }

    private List<ProductResponse.ProductIdDto> getRandomProductsByInterestedCategory(List<Long> recentProducts) {
        Map<Long, Integer> categoryCountMap = new HashMap<>();

        for (Long productId : recentProducts) {
            Long categoryId = categoryProductRepository.findByProductId(productId).getCategory().getId();
            categoryCountMap.put(categoryId, categoryCountMap.getOrDefault(categoryId, 0) + 1);
        }

        Long interestedCategoryId = getKeyWithMaxValue(categoryCountMap);

        List<CategoryProduct> categoryProducts = categoryProductRepository.findRandomByCategoryId(interestedCategoryId);

        List<ProductResponse.ProductIdDto> resultProducts = new java.util.ArrayList<>(
            categoryProducts.stream()
                .map(categoryProduct -> ProductConverter.toDto(categoryProduct.getProduct()))
                .toList());

        // 현재 카테고리 상품의 개수
        int currentSize = resultProducts.size();
        int desiredSize = 12; // 원하는 리스트의 최종 크기

        if (currentSize < desiredSize) {
            // 부족한 개수만큼 랜덤 상품을 추가로 가져오기
            int additionalProductsNeeded = desiredSize - currentSize;
            List<Product> additionalRandomProducts = productRepository.findRandomProducts(additionalProductsNeeded);
            // 추가된 랜덤 상품을 결과 리스트에 추가
            List<ProductResponse.ProductIdDto> additionalProducts = additionalRandomProducts.stream()
                .map(ProductConverter::toDto)
                .toList();
            resultProducts.addAll(additionalProducts);
        }

        return resultProducts;
    }
}