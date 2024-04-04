package spharos.msg.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.product.repository.ProductOptionRepository;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

    //해당 상품의 모든 옵션 id를 받음
    public ApiResponse<?> getAllOptionId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();

        return ApiResponse.of(SuccessStatus.OPTION_ID_SUCCESS,
            productOptionRepository.findByProduct(product)
                .stream()
                .map(ProductOption::getId)
                .toList());
    }
}
