package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("리소스 로더 관련 기능")
class ResourceLoaderTest {
    @DisplayName("templates폴더에 있는 리소스의 url이 주어지면, 해당 리소스를 불러온다.")
    @Test
    void getBytes() {
        // given
        String url = "/user/list.html";
        byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/user/list.html");

        // when
        byte[] actual = ResourceLoader.getBytes(url);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("static폴더에 있는 리소스의 url이 주어지면, 해당 리소스를 불러온다.")
    @Test
    void getBytes2() {
        // given
        String url = "/js/scripts.js";
        byte[] expected = FileIoUtils.loadFileFromClasspath("./static/js/scripts.js");

        // when
        byte[] actual = ResourceLoader.getBytes(url);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
