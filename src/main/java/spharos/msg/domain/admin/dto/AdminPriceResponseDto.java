package spharos.msg.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AdminPriceResponseDto {

    @Builder
    @AllArgsConstructor
    @Getter
    public static class TotalSalesPrice {
        private Long totalSalesPrice;
    }
}
