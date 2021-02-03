package utils;

import exception.FileNotExistException;
import model.User;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileIoUtilsTest {

    @Test
    void byteArrayAndString() {
        String body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        String body2 = FileIoUtils.loadFileFromClasspath("./templates/index.html", StandardCharsets.UTF_8);

        assertThat(body.getBytes(StandardCharsets.UTF_8)).isEqualTo(body2.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void byteArrayAndString2() {
        String body = FileIoUtils.loadFileFromClasspath("./static/fonts/glyphicons-halflings-regular.ttf", StandardCharsets.ISO_8859_1);
        assertThat(body.getBytes()).isNotEqualTo(body.getBytes(StandardCharsets.ISO_8859_1));
    }

    @Test
    void loadHandlebar() {
        String result = FileIoUtils.loadHandleBarFromClasspath(
                "user/list",
                Arrays.asList(new User("corby", "corby123", "김범준", "corby@kakao.com"))
        );

        assertThat(result)
                .contains("corby", "김범준", "corby@kakao.com")
                .doesNotContain("corby123");
    }

    @Test
    void loadFileFail() {
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath("not/exist"))
                .isInstanceOf(FileNotExistException.class);
    }

    @Test
    void loadHandlebarFail() {
        assertThatThrownBy(() -> FileIoUtils.loadHandleBarFromClasspath("not/exist", new Object()))
                .isInstanceOf(FileNotExistException.class);
    }
}
