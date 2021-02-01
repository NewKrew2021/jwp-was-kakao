package webserver;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("사용자 관련 기능")
public class UserControllerTest {

    @DisplayName("GET 메서드로 사용자 정보를 받으면 사용자 정보를 디비에 저장한다.")
    @Test
    void handler() {
        // given
        HttpMethod method = HttpMethod.GET;
        String url = "/user/create?userId=jordy&password=1q2w3e4r!&name=죠르디&email=jordy@kakaocorp.com";
        String body = "";
        User expected = User.of("jordy", "1q2w3e4r!", "죠르디", "jordy@kakaocorp.com");

        // when
        UserController.handle(method, url, body);
        User actual = DataBase.findUserById("jordy");

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("POST 메서드로 사용자 정보를 받으면 사용자 정보를 디비에 저장한다.")
    @Test
    void handle2() {
        // given
        HttpMethod method = HttpMethod.POST;
        String url = "/user/create";
        String body = "userId=jordy&password=1q2w3e4r!&name=죠르디&email=jordy@kakaocorp.com";
        User expected = User.of("jordy", "1q2w3e4r!", "죠르디", "jordy@kakaocorp.com");

        // when
        UserController.handle(method, url, body);
        User actual = DataBase.findUserById("jordy");

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
