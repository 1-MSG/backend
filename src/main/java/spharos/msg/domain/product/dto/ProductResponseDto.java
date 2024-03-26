package spharos.msg.domain.product.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import lombok.Setter;

@Data
@Builder
@Setter(AccessLevel.NONE)
public class ProductResponseDto {
    @Data
    @Builder
    public static class HomeCosmeRandomFood {

        private List<ProductInfoDto> cosmeticList;
        private List<ProductInfoDto> randomList;
        private List<ProductInfoDto> foodList;
    }
    @Data
    @Builder
    public static class HomeFashion {

        private List<ProductInfoDto> fashionList;
    }
}
