package model.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {
    @Test
    void invalidUserId() {
        assertThatThrownBy(() -> {
            new User("", "pwd", "name", "a@a.com");
        }).isInstanceOf(InvalidUserIdException.class);
    }

    @Test
    void invalidPassword() {
        assertThatThrownBy(() -> {
            new User("userId", "", "name", "a@a.com");
        }).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void invalidName() {
        assertThatThrownBy(() -> {
            new User("userId", "pwd", "", "a@a.com");
        }).isInstanceOf(InvalidNameException.class);
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() -> {
            new User("userId", "pwd", "name", "");
        }).isInstanceOf(InvalidEmailException.class);
    }
}