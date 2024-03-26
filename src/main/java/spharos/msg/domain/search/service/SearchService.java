package spharos.msg.domain.search.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDto;
import spharos.msg.domain.search.repository.SearchRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final SearchRepository searchRepository;

    public List<SearchProductDto> findMatchProducts(String keyword, int index) {
        List<Product> products = searchRepository.searchAllProduct(keyword, index);
        return products
            .stream()
            .map(this::toSearchProductDto)
            .toList();
    }

    private SearchProductDto toSearchProductDto(Product product) {
        return SearchProductDto.builder()
            .productId(product.getId())
            .productName(product.getProductName())
            .productPrice(product.getProductPrice())
            .discountRate(product.getDiscountRate())
            // TODO: 현재 이미지를 불러오는 로직이 없기 때문에 임시 정적 이미지를 넣어둠
            .image(
                "https://i.namu.wiki/i/DIWQPMFg_xE7JxIv0-4M5PbXco2d-BynsivSWqt6enqDgXOKw0nuZznBUGV-7FtJilQEY7zxodgkZcYlQXDJw.webp")
            // TODO: isLike 테이블이 어떻게 될지 모르기 때문에 일단 정적으로 넣어
            .isLike(false)
            .productStar(new BigDecimal("4.5")) //TODO: 아직 없어서 정적으로 넣어둠.
            .build();
    }

}
