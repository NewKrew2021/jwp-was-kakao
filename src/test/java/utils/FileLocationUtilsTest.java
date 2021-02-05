package utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileLocationUtilsTest {

    @ParameterizedTest
    @CsvSource({"/abc.ico", "/abcd.html"})
    void templateLocationTest(String test) {
        assertThat(FileLocationUtils.getContentLocation(test)).isEqualTo("./templates" + test);
    }

    @ParameterizedTest
    @CsvSource({"/abc.jpg", "/abcd.css", "/ddd.js"})
    void staticLocationTest(String test) {
        assertThat(FileLocationUtils.getContentLocation(test)).isEqualTo("./static" + test);
    }
}
