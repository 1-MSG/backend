package spharos.msg.domain.bundle.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.bundle.dto.BundleResponse;
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
    public BundleResponse.BundlesDto getBundles(int page, int size) {
        //페이저블 객체 만들기
        PageRequest pageRequest = PageRequest.of(page, size);
        //번들이 담긴 페이지 불러오기
        Slice<Bundle> bundleSlice = bundleRepository.findAll(pageRequest);
        //Dto 리스트로 변환
        List<BundleResponse.BundleDto> bundles = getBundleList(bundleSlice);
        //다음 페이지가 있는지 확인
        boolean isLast = !bundleSlice.hasNext();

        return BundleResponse.BundlesDto.builder()
            .bundles(bundles)
            .isLast(isLast)
            .build();
    }

    @Transactional
    public List<Long> getBundleProducts(Long bundleId) {

        return bundleProductRepository.findAllByBundleId(bundleId).stream()
            .map(bundleProduct -> bundleProduct.getProduct().getId()).toList();
    }

    private List<BundleResponse.BundleDto> getBundleList(Slice<Bundle> bundleSlice) {
        return bundleSlice.getContent().stream().map(bundle -> BundleResponse.BundleDto.builder()
            .bundleId(bundle.getId())
            .bundleName(bundle.getBundleName())
            .brandName(bundle.getBrandName())
            .bundleImage(bundle.getBundleImage())
            .bundlePrice(getBundlePrice(bundle))
            .build()).toList();
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
        // 정수로 변환하여 반환
        return discountedPrice.intValue();
    }
}
