package webserver.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestPathTest {
    @ParameterizedTest
    @MethodSource("getRequestPath")
    public void getFilePath(String path, String expectedFilePath) {
        RequestPath requestPath = new RequestPath(path);

        assertThat(requestPath.asFilePath()).isEqualTo(expectedFilePath);
        assertThat(requestPath.requiresFile()).isEqualTo(true);
    }

    private static Stream<Arguments> getRequestPath() {
        return Stream.of(Arguments.arguments("/index.html", "./templates/index.html"),
                Arguments.arguments("/favicon.ico", "./templates/favicon.ico"),
                Arguments.arguments("/css/bootstrap.min.css", "./static/css/bootstrap.min.css"),
                Arguments.arguments("/js/jquery-2.2.0.min.js", "./static/js/jquery-2.2.0.min.js"));
    }

    @Test
    public void requiresCss() {
        RequestPath path = new RequestPath("/css/bootstrap.min.css");

        assertThat(path.requiresCss()).isEqualTo(true);
        assertThat(path.startsWith("/css")).isEqualTo(true);
    }

}