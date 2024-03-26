package spharos.msg.global.api.example;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExampleResponse {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TestDto {
        String testJson;
    }
}
