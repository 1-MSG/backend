package spharos.msg.domain.bundle.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import spharos.msg.domain.bundle.convertor.BundleConvertor;
import spharos.msg.domain.bundle.dto.BundleResponse;
import spharos.msg.domain.bundle.dto.BundleResponse.BundleDto;
import spharos.msg.domain.bundle.entity.Bundle;
import spharos.msg.domain.bundle.entity.BundleProduct;
import spharos.msg.domain.bundle.repository.BundleProductRepository;
import spharos.msg.domain.bundle.repository.BundleRepository;

@Service
@RequiredArgsConstructor
public class BundleService {

    private final BundleRepository bundleRepository;
    private final BundleProductRepository bundleProductRepository;

    @Transactional
    public BundleResponse.BundlesDto getBundles(Pageable pageable) {

        Page<Bundle> bundlePage = bundleRepository.findAll(pageable);

        List<BundleResponse.BundleDto> bundles = getBundleList(bundlePage);
        boolean isLast = !bundlePage.hasNext();

        return BundleConvertor.toDto(bundles, isLast);
    }

    @Transactional
    public BundleDto getBundleInfo(Long bundleId) {
        Bundle bundle = bundleRepository.findById(bundleId).orElseThrow(()->new NotFoundException(bundleId+"id에 해당하는 번들을 찾을 수 없음"));
        Integer bundlePrice = getBundlePrice(bundle);
        List<Long> bundleProductIds = bundleProductRepository.findAllByBundleId(bundleId).stream()
            .map(bundleProduct -> bundleProduct.getProduct().getId()).toList();
        return BundleConvertor.toDto(bundle, bundlePrice, bundleProductIds);
    }

    private List<BundleResponse.BundleDto> getBundleList(Page<Bundle> bundlePage) {
        return bundlePage.getContent().stream().map(bundle -> {
            Integer bundlePrice = getBundlePrice(bundle);
            return BundleConvertor.toDto(bundle, bundlePrice);
        }).toList();
    }

    private Integer getBundlePrice(Bundle bundle) {

        List<BundleProduct> bundleProducts = bundleProductRepository.findAllByBundle(bundle);

        return bundleProducts.stream()
            .mapToInt(bundleProduct -> {
                //제품 가격
                Integer productPrice = bundleProduct.getProduct().getProductPrice();
                //할인율
                BigDecimal discountRate = bundleProduct.getProduct().getDiscountRate();
                //최종 가격 계산 후 반환
                return getDiscountedPrice(productPrice, discountRate);
            })
            .min() //최소값 찾기
            .orElse(0);  // 값이 없을 경우 기본값 0으로 설정;
    }

    private Integer getDiscountedPrice(Integer price, BigDecimal rate) {
        // 할인율 실수로 변환
        BigDecimal decimalRate = rate.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        // 할인 가격 계산
        BigDecimal discountedPrice = BigDecimal.valueOf(price)
            .multiply(BigDecimal.ONE.subtract(decimalRate));
        // 정수로 변환 하여 반환
        return discountedPrice.intValue();
    }
}
