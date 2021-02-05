package utils;

import model.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("리소스 로더 관련 기능")
class ResourceLoaderTest {
    @DisplayName("templates폴더에 있는 리소스의 url이 주어지면, 해당 리소스를 불러온다.")
    @Test
    void getBytes() throws IOException, URISyntaxException {
        // given
        String url = "/user/list.html";
        byte[] bytes = FileIoUtils.loadFileFromClasspath("./templates/user/list.html");
        Resource expected = Resource.of(bytes, "html");

        // when
        Resource actual = ResourceLoader.getResource(url);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("static폴더에 있는 리소스의 url이 주어지면, 해당 리소스를 불러온다.")
    @Test
    void getBytes2() throws IOException, URISyntaxException {
        // given
        String url = "/js/scripts.js";
        byte[] bytes = FileIoUtils.loadFileFromClasspath("./static/js/scripts.js");
        Resource expected = Resource.of(bytes, "js");

        // when
        Resource actual = ResourceLoader.getResource(url);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
