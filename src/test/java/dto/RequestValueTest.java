package dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestValueTest {

    @DisplayName("RequestValue 생성 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "GET /index.html HTTP/1.1 | ",
            "POST /user/create HTTP/1.1 | userId=javajigi&password=password"
    }, delimiter = '|')
    void create(String header, String body) {
        RequestValue requestValue = new RequestValue(Arrays.asList(header), body);

        assertThat(requestValue)
                .isEqualToComparingFieldByField(new RequestValue(Arrays.asList(header), body));
    }
}
