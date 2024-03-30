package spharos.msg.domain.product.service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import spharos.msg.domain.category.repository.CategoryProductRepository;
import spharos.msg.domain.product.dto.ProductDetailReponse;
import spharos.msg.domain.product.dto.ProductResponse;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductImage;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.product.repository.ProductDetailImageRepository;
import spharos.msg.domain.product.repository.ProductImageRepository;
import spharos.msg.domain.product.repository.ProductOptionRepository;
import spharos.msg.domain.product.repository.ProductRepository;
import java.util.List;
import spharos.msg.domain.product.repository.ProductSalesInfoRepository;
import spharos.msg.domain.review.entity.Review;
import spharos.msg.domain.review.entity.ReviewImage;
import spharos.msg.domain.review.repository.ReviewImageRepository;
import spharos.msg.domain.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSalesInfoRepository productSalesInfoRepository;
    private final ProductOptionRepository productOptionRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final ReviewRepository reviewRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductDetailImageRepository productDetailImageRepository;
    private final ReviewImageRepository reviewImageRepository;

    //Home화면 상품 조회 중 뷰티,랜덤,음식 상품 불러오기
//    public ProductResponse.HomeCosmeRandomFoodDto getHomeCosmeRandomFood() {
//
//        List<Product> beautyProducts = productRepository.findProductsByCategoryName("뷰티");
//        List<Product> foodProducts = productRepository.findProductsByCategoryName("신선식품");
//        List<Product> randomProducts = productRepository.findRandomProducts();
//
//        List<ProductResponse.ProductInfo> beautys = beautyProducts.stream()
//            .limit(6)
//            .map(this::mapToProductInfoDto)
//            .toList();
//
//        List<ProductResponse.ProductInfo> randoms = randomProducts.stream()
//            .limit(12)
//            .map(this::mapToProductInfoDto)
//            .toList();
//
//        List<ProductResponse.ProductInfo> foods = foodProducts.stream()
//            .limit(12)
//            .map(this::mapToProductInfoDto)
//            .toList();
//
//        return ProductResponse.HomeCosmeRandomFoodDto.builder()
//            .cosmeticList(beautys)
//            .randomList(randoms)
//            .foodList(foods)
//            .build();
//    }

    //Home화면 상품 조회 중 패션 상품 불러오기
//    public ProductResponse.HomeFashionDto getHomeFashion(int index) {
//        // 한 페이지에 들어갈 상품의 개수
//        int SIZE = 16;
//        //pageble 객체 생성
//        Pageable pageable = PageRequest.of(index, SIZE);
//        //index 기반 패션 상품들 조회
//        Page<Product> fashionProductsPage = productRepository.findFashionProducts(pageable);
//        List<Product> fashionProducts = fashionProductsPage.getContent();
//
//        // 조회된 상품들 ProductInfoDto 리스트로 변환
//        List<ProductResponse.ProductInfo> fashions = fashionProducts.stream()
//            .map(this::mapToProductInfoDto)
//            .toList();
//
//        return ProductResponse.HomeFashionDto.builder()
//            .fashionList(fashions)
//            .build();
//    }

    //id로 상품의 기본 정보 불러오기
    @Transactional
    public ProductResponse.ProductInfo getProductInfo(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException(productId+"해당 상품을 찾을 수 없음"));

        Integer discountPrice = getDiscountedPrice(product.getProductPrice(), product.getDiscountRate());

        return ProductResponse.ProductInfo.builder()
            .productBrand(product.getBrand().getBrandName())
            .productName(product.getProductName())
            .productPrice(product.getProductPrice())
            .productStar(product.getProductSalesInfo().getProductStar())
            .discountRate(product.getDiscountRate())
            .discountPrice(discountPrice)
            .build();
    }

    //id로 상품 썸네일 이미지 불러오기
    @Transactional
    public ProductResponse.ProductImage getProductImage(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException(productId+"해당 상품을 찾을 수 없음"));

        ProductImage productImage = productImageRepository.findByProductAndImageIndex(product, 0)
            .orElseThrow(() -> new NotFoundException("해당 상품에 대한 index가 0인 이미지를 찾을 수 없음"));

        return ProductResponse.ProductImage.builder()
            .productImageUrl(productImage.getProductImageUrl())
            .productImageDescription(productImage.getProductImageDescription())
            .build();
    }

    //id로 상품 상세 html 불러오기
    @Transactional
    public String getProductDetail(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException(productId+"해당 상품을 찾을 수 없음"));

        return product.getProductInfoDetail().getProductInfoDetailContent();
    }

    private Integer getDiscountedPrice(Integer price, BigDecimal discountRate){

        if (price == null || discountRate == null) {
            return price;
        }

        BigDecimal normalPrice = new BigDecimal(price);
        BigDecimal discount = discountRate.divide(BigDecimal.valueOf(100)); //할인율을 백분율로 변환
        BigDecimal discountedPrice = normalPrice.multiply(BigDecimal.ONE.subtract(discount)); // 할인 적용 가격 계산

        return discountedPrice.intValue();
    }
}
