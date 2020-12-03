package apps.slipp.service;

import apps.slipp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoginServiceTest {

    LoginService service;

    @BeforeEach
    void setUp(){
        service = new LoginService();
    }

    @DisplayName("아이디가 존재하지 않으면 exception 을 던진다")
    @Test
    void notExist(){
        assertThatThrownBy(() -> service.login("notExist", "1111"))
                .isInstanceOf(NotExistUserException.class);

    }

    @DisplayName("패스워드가 일치하지 않으면 exception 을 던진다")
    @Test
    void invalidPassword(){
        createUser("nio", "1111");

        assertThatThrownBy(() -> service.login("nio", "2222"))
                .isInstanceOf(InvalidPasswordException.class);
    }

    private void createUser(String userId, String password) {
        new SignUpService().signUp(new User(userId, password, "test", "test@kakao.com"));
    }

}