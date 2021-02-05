package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseUtilsTest {

    @DisplayName("경로가 들어오면, 해당 자원의 종류에 따라서 content-type을 반환한다.")
    @Test
    void findContentTypeTest() {
        assertThat(HttpResponseUtils.findContentType("/index.html")).isEqualTo("text/html;charset=UTF-8");
    }

    @DisplayName("경로가 들어오면, 해당 자원의 종류에 따라서 자원의 경로를 반환한다.")
    @Test
    void findPathTest() {
        assertThat(HttpResponseUtils.findPath("/index.html")).isEqualTo("./templates/index.html");
    }

}
