package webserver.http.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class FileExtentionsTest {

    @DisplayName("path 에서 확장자를 추출할 수 있다")
    @ParameterizedTest
    @CsvSource(value = {"/index.html:html", "main.min.js:js", "/js/main.min.js:js"}, delimiterString = ":")
    void fromPath1(String path, String expected){
        String extenstion = FileExtentions.fromPath(path);
        assertThat(extenstion).isEqualTo(expected);
    }

}