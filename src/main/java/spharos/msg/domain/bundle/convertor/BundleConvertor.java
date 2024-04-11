package spharos.msg.domain.bundle.convertor;

import java.util.List;
import spharos.msg.domain.bundle.dto.BundleResponse;
import spharos.msg.domain.bundle.dto.BundleResponse.BundleDto;
import spharos.msg.domain.bundle.dto.BundleResponse.BundlesDto;
import spharos.msg.domain.bundle.entity.Bundle;

public class BundleConvertor {

    public static BundlesDto toDto(List<BundleDto> bundles, boolean isLast) {
        return BundleResponse.BundlesDto.builder()
            .bundles(bundles)
            .isLast(isLast)
            .build();
    }

    public static BundleDto toDto(Bundle bundle, Integer bundlePrice) {
        return BundleResponse.BundleDto.builder()
            .bundleId(bundle.getId())
            .bundleName(bundle.getBundleName())
            .brandName(bundle.getBrandName())
            .bundleImage(bundle.getBundleImage())
            .bundlePrice(bundlePrice)
            .build();
    }

    public static BundleDto toDto(Bundle bundle, Integer bundlePrice, List<Long> bundleProductIds) {
        return BundleResponse.BundleDto.builder()
            .bundleId(bundle.getId())
            .bundleName(bundle.getBundleName())
            .brandName(bundle.getBrandName())
            .bundleImage(bundle.getBundleImage())
            .bundlePrice(bundlePrice)
            .bundleProductIds(bundleProductIds)
            .build();
    }
}
