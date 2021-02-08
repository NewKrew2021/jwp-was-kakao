package webserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

public class BodyTest {
    private static byte[] content;

    @BeforeAll
    static void beforeAll() {
        MockedStatic<FileIoUtils> utils = mockStatic(FileIoUtils.class);
        utils.when(() -> FileIoUtils.loadFileFromClasspath("./templates/test.html"))
                .thenReturn("Hello World".getBytes(StandardCharsets.UTF_8));

        utils.when(() -> FileIoUtils.loadFileFromClasspath("./templates/test.css"))
                .thenReturn("Bye World".getBytes(StandardCharsets.UTF_8));

        try {
            content = FileIoUtils.loadFileFromClasspath("./templates/test.html");
        } catch (IOException | URISyntaxException ignored) {
            throw new RuntimeException("Could not mock FileIoUtils");
        }
    }

    @DisplayName("Body를 만드는 두 가지 방법으로 올바른 메시지가 전달되는지 확인")
    @Test
    void testIsCorrectBody() {
        assertThat(new String(new Body(content, "text/html").getBody())).isEqualTo("Hello World");
        assertThat(new String(new Body("./templates/test.html").getBody())).isEqualTo("Hello World");
    }

    @DisplayName("Body를 만드는 두 가지 방법으로 content-length가 맞는지 확인")
    @Test
    void testContentLength() {
        assertThat(new Body(content, "text/html").getContentLength()).isEqualTo(11);
        assertThat(new Body("./templates/test.html").getContentLength()).isEqualTo(11);
    }

    @DisplayName("Body를 만드는 두 가지 방법으로 content-type이 맞는지 확인")
    @Test
    void testContentType() {
        assertThat(new Body(content, "text/html").getContentType()).isEqualTo("text/html");
        assertThat(new Body("./templates/test.html").getContentType()).isEqualTo("text/html");
        assertThat(new Body("./templates/test.css").getContentType()).isEqualTo("text/css");
    }
}