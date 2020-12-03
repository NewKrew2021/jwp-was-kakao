package apps.slipp.service;

import apps.slipp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SignUpServiceTest {

    SignUpService service;

    @BeforeEach
    void setUp(){
        service = new SignUpService();
    }

    @DisplayName("이미 가입되어 있다면 exception 을 던진다")
    @Test
    void alreadySignUp(){
        service.signUp(new User("nio", "1111", "니오", "nio@kakao.com"));

        assertThatThrownBy( () -> service.signUp(new User("nio", "1111", "니오", "nio@kakao.com")) )
                .isInstanceOf(UserAlreadySignUpException.class);

    }

}