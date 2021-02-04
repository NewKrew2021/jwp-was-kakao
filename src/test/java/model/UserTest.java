package model;

import exception.user.WrongEmailException;
import exception.user.WrongIdException;
import exception.user.WrongNameException;
import exception.user.WrongPasswordException;
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
                .isInstanceOf(WrongIdException.class);
    }

    @Test
    public void newUserTest_wrongPassword() {
        assertThatThrownBy(() -> new User("id", "", "na", "em@em.com"))
                .isInstanceOf(WrongPasswordException.class);
    }

    @Test
    public void newUserTest_wrongName() {
        assertThatThrownBy(() -> new User("id", "pw", "", "em@em.com"))
                .isInstanceOf(WrongNameException.class);
    }

    @Test
    public void newUserTest_wongEmail() {
        assertThatThrownBy(() -> new User("id", "pw", "na", "em@em."))
                .isInstanceOf(WrongEmailException.class);
    }
}
