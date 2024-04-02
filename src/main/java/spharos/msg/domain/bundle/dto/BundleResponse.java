package spharos.msg.domain.bundle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Builder
@Setter(AccessLevel.NONE)
public class BundleResponse {

    @Data
    @Builder
    @Setter(AccessLevel.NONE)
    public static class BundlesDto {
        private List<BundleDto> bundles;

        @JsonProperty("isLast")
        @Getter(AccessLevel.NONE)
        private boolean isLast;
    }

    @Data
    @Builder
    @Setter(AccessLevel.NONE)
    public static class BundleDto {
        private Long bundleId;

        private String bundleName;

        private String brandName;

        private String bundleImage;

        private Integer bundlePrice;
    }
}
