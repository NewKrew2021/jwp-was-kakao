package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserTest {
    @Test
    public void newUserTest_success() {
        assertDoesNotThrow(() -> new User("id", "pw", "name", "email@gmail.com"));
    }

    @Test
    public void newUserTest_wrongId() {
        assertThatThrownBy(() ->
            new User("", "pw", "na", "em@em.com"))
                .isInstanceOf(RuntimeException.class).hasMessage("올바르지 않은 ID입니다.");

    }

    @Test
    public void newUserTest_wrongPassword() {
        assertThatThrownBy(() -> new User("id", "", "na", "em@em.com"))
                .isInstanceOf(RuntimeException.class).hasMessage("올바르지 않은 비밀번호입니다.");
    }

    @Test
    public void newUserTest_wrongName() {
        assertThatThrownBy(() -> new User("id", "pw", "", "em@em.com"))
                .isInstanceOf(RuntimeException.class).hasMessage("올바르지 않은 이름입니다.");
    }

    @Test
    public void newUserTest_wongEmail() {
        assertThatThrownBy(() -> new User("id", "pw", "na", "em@em."))
                .isInstanceOf(RuntimeException.class).hasMessage("올바르지 않은 이메일입니다.");
    }
}
