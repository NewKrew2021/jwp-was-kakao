package webserver.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ParametersTest {
    @ParameterizedTest
    @CsvSource({"userId,jayk","something,0"})
    void createTest(String key, String expected) {
        Parameters parameters = new Parameters("userId=jayk&password=jayk.yang",
                "userId=jayk&password=jayk.yang&name=jayk&email=jayk@kakao.com");

        assertThat(parameters.get(key,"0")).isEqualTo(expected);
    }
}