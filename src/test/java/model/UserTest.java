package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class UserTest {

    @DisplayName("User 생성 테스트")
    @Test
    void create1() {
        User user = new User("javajigi", "password", "포비", "javajigi@slipp.net");

        assertThat(user).isEqualTo(
                new User("javajigi", "password", "포비", "javajigi@slipp.net"));
    }

    @DisplayName("User 생성 테스트 : 빈 값인 경우 에러 발생")
    @ParameterizedTest
    @NullAndEmptySource
    void create2(String input) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new User(input, input, input, input);
        });
    }
}
