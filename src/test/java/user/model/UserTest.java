package user.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserTest {

    @Test
    @DisplayName("아이디가 비어있을 경우")
    void noIdTest() {
        assertThatThrownBy(() -> new User("", "123", "123", "123@gmail.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Please check userId");
    }

    @Test
    @DisplayName("비밀번호가 비어있을 경우")
    void noPasswordTest() {
        assertThatThrownBy(() -> new User("123", "", "123", "123@gmail.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Please check password");
    }

    @Test
    @DisplayName("이름이 비어있을 경우")
    void noNameTest() {
        assertThatThrownBy(() -> new User("123", "123", "", "123@gmail.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Please check name");
    }

    @Test
    @DisplayName("이메일 형식이 맞지 않을 경우")
    void noEmailTest() {
        assertThatThrownBy(() -> new User("123", "123", "123", "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Please check email");
    }

    @Test
    @DisplayName("비밀번호 테스트")
    void samePasswordTest() {
        User user = new User("jayk", "123", "jayk.yang", "jayk.yang@abc.com");

        assertThat(user.same("123")).isTrue();
    }
}