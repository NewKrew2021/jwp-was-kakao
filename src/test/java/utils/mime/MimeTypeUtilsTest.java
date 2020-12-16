package utils.mime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MimeTypeUtilsTest {
    @ParameterizedTest
    @MethodSource("getMimes")
    void findMimeTypes(String filePath, String expectedMimeType) {
        String mimeType = MimeTypeUtils.getMimeType(filePath);

        assertThat(mimeType).isEqualTo(expectedMimeType);
    }

    private static Stream<Arguments> getMimes() {
        return Stream.of(
                arguments("test.css", "text/css"),
                arguments("test.js", "application/js"),
                arguments("test.html", "text/html"),
                arguments("test.jpg", "image/jpeg"),
                arguments("favicon.ico", "image/x-icon"),
                arguments("font.woff2", "font/woff2")
        );
    }
}