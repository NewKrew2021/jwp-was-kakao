package utils;

import exception.FileNotExistException;
import model.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileIoUtilsTest {

    @Test
    void load() {
        FileIoUtils.loadFileFromClasspath("./templates/index.html");
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
