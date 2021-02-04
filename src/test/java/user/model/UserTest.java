package user.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import user.exceptions.IllegalUserValuesException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {
    private final String VALID_USER_ID = "123";
    private final String VALID_PASSWORD = "password";
    private final String VALID_NAME = "testName";
    private final String VALID_EMAIL = "test@email.com";

    @DisplayName("모든 입력이 유효하다")
    @Test
    void testValidUser() {
        assertThatCode(() -> new User(VALID_USER_ID, VALID_PASSWORD, VALID_NAME, VALID_EMAIL))
                .doesNotThrowAnyException();
    }

    @DisplayName("아이디가 유효하지 않다")
    @Test
    void testInvalidUserId() {
        assertThatThrownBy(() -> new User(" ", VALID_PASSWORD, VALID_NAME, VALID_EMAIL))
                .isInstanceOf(IllegalUserValuesException.class);
    }

    @DisplayName("비밀번호가 유효하지 않다")
    @Test
    void testInvalidPassword() {
        assertThatThrownBy(() -> new User(VALID_USER_ID, "\n", VALID_NAME, VALID_EMAIL))
                .isInstanceOf(IllegalUserValuesException.class);
    }

    @DisplayName("이름이 유효하지 않다")
    @Test
    void testInvalidName() {
        assertThatThrownBy(() -> new User(VALID_USER_ID, VALID_PASSWORD, "\t", VALID_EMAIL))
                .isInstanceOf(IllegalUserValuesException.class);
    }

    @DisplayName("이메일이 유효하지 않다")
    @ParameterizedTest
    @ValueSource(strings = {"test@email", "#test@email.com", "test@email.", " test@email.com"})
    void testInvalidEmail(String email) {
        assertThatThrownBy(() -> new User(VALID_USER_ID, VALID_PASSWORD, VALID_NAME, email))
                .isInstanceOf(IllegalUserValuesException.class);
    }
}
