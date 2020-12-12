package resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceResolverTest {
    @ParameterizedTest
    @ValueSource(strings = {"/css/test.css",
            "/js/test.js",
            "/fonts/font.wott",
            "/images/test.png"})
    void staticPath(String inputPath) {
        String path = ResourceResolver.resolve(inputPath);

        assertThat(path).isEqualTo("./static" + inputPath);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test.html",
            "favicon.ico"})
    void templatePath(String inputPath) {
        String path = ResourceResolver.resolve(inputPath);

        assertThat(path).isEqualTo("./templates" + inputPath);
    }

    @Test
    void defaultPath() {
        String inputPath = "test.co";
        String path = ResourceResolver.resolve(inputPath);

        assertThat(path).isEqualTo(inputPath);
    }
}