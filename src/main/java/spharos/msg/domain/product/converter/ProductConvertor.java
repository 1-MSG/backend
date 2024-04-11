package spharos.msg.domain.product.converter;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.domain.category.entity.CategoryProduct;
import spharos.msg.domain.product.dto.ProductResponse;
import spharos.msg.domain.product.dto.ProductResponse.Best11Dto;
import spharos.msg.domain.product.dto.ProductResponse.BestProductsDto;
import spharos.msg.domain.product.dto.ProductResponse.ProductCategoryDto;
import spharos.msg.domain.product.dto.ProductResponse.ProductDeliveryDto;
import spharos.msg.domain.product.dto.ProductResponse.ProductIdDto;
import spharos.msg.domain.product.dto.ProductResponse.ProductImageDto;
import spharos.msg.domain.product.dto.ProductResponse.ProductInfoDto;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductImage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConvertor {
    public static ProductInfoDto toDto(Product product,Integer discountPrice){
        return ProductResponse.ProductInfoDto.builder()
            .brandName(product.getBrand().getBrandName())
            .brandId(product.getBrand().getId())
            .productName(product.getProductName())
            .productPrice(product.getProductPrice())
            .productStar(product.getProductSalesInfo().getProductStar())
            .discountRate(product.getDiscountRate())
            .discountPrice(discountPrice)
            .reviewCount(product.getProductSalesInfo().getReviewCount())
            .responseTime(String.valueOf(System.currentTimeMillis()))
            .build();
    }

    public static ProductInfoDto toDto(Product product,ProductImage productImage, Integer discountPrice){
        return ProductResponse.ProductInfoDto.builder()
            .productImage(productImage.getProductImageUrl())
            .brandName(product.getBrand().getBrandName())
            .brandId(product.getBrand().getId())
            .productName(product.getProductName())
            .productPrice(product.getProductPrice())
            .productStar(product.getProductSalesInfo().getProductStar())
            .discountRate(product.getDiscountRate())
            .discountPrice(discountPrice)
            .reviewCount(product.getProductSalesInfo().getReviewCount())
            .responseTime(String.valueOf(System.currentTimeMillis()))
            .build();
    }

    public static ProductImageDto toDto(ProductImage productImage){
        return ProductResponse.ProductImageDto.builder()
            .productImageUrl(productImage.getProductImageUrl())
            .productImageDescription(productImage.getProductImageDescription())
            .build();
    }

    public static ProductCategoryDto toDto(CategoryProduct categoryProduct){
        return  ProductResponse.ProductCategoryDto.builder()
            .categoryMid(categoryProduct.getCategory().getCategoryName())
            .categoryLarge(categoryProduct.getCategory().getParent().getCategoryName())
            .build();
    }

    public static ProductIdDto toDto(Product product){
        return  ProductResponse.ProductIdDto.builder().productId(product.getId())
            .build();
    }

    public static BestProductsDto toDto(List<ProductResponse.ProductIdDto> productList, boolean isLast){
        return ProductResponse.BestProductsDto.builder()
            .productList(productList).isLast(isLast).build();
    }

    public static Best11Dto toDto(Product product, ProductImage productImage){
        return ProductResponse.Best11Dto.builder()
            .productId(product.getId())
            .productName(product.getProductName())
            .productBrand(product.getBrand().getBrandName())
            .productPrice(product.getProductPrice())
            .productImage(productImage.getProductImageUrl())
            .productSellTotalCount(product.getProductSalesInfo().getProductSellTotalCount())
            .build();
    }
    
}
