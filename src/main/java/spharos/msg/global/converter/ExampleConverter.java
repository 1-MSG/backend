package spharos.msg.global.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.global.api.example.ExampleResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExampleConverter {
    public static ExampleResponse.TestDto toExampleTestDto() {
        return ExampleResponse.TestDto
                .builder()
                .testJson("This is Test")
                .build();
    }
}