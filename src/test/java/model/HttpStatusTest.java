package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class HttpStatusTest {

    @DisplayName("HttpStatus description 테스트")
    @ParameterizedTest
    @CsvSource(value = {
        "HTTP_OK, 200 OK",
        "HTTP_FOUND, 302 FOUND"
    })
    void descHttpStatusCode(HttpStatus httpStatus, String expectedDescHttpStatusCode) {
        assertThat(httpStatus.descHttpStatusCode()).isEqualTo(expectedDescHttpStatusCode);
    }
}
