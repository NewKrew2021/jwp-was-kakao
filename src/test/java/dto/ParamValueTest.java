package dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ParamValueTest {


    @DisplayName("ParamValue 생성 테스트 : 빈 값인 경우 에러 발생")
    @Test
    void create1() {
        Optional<String> optionalParam = Optional.empty();

        assertThatIllegalArgumentException().isThrownBy(
                () -> ParamValue.of(optionalParam));
    }

    @DisplayName("ParamValue 생성 테스트")
    @Test
    void create2() {
        Optional<String> optionalParam = Optional.of(
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        ParamValue paramValue = ParamValue.of(optionalParam);
        assertThat(paramValue).isEqualToComparingFieldByField(ParamValue.of(optionalParam));
    }
}
