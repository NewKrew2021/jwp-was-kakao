package webserver;

import db.DataBase;
import model.RequestMessage;
import model.Response;
import model.ResponseFound;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("사용자 관련 기능")
public class UserControllerTest {

    @DisplayName("GET 메서드로 사용자 정보를 받으면 사용자 정보를 디비에 저장하고 Found를 응답한다.")
    @Test
    void handler() {
        // given
        String requestLine = "GET /user/create?userId=jordy&password=1q2w3e4r!&name=죠르디&email=jordy@kakaocorp.com HTTP/1.1\n";
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Host", "localhost:8080");
        requestHeader.put("Connection", "keep-alive");
        requestHeader.put("Accept", "*/*");
        String requestBody = "";

        RequestMessage requestMessage = RequestMessage.of(requestLine, requestHeader, requestBody);
        User expected = User.of("jordy", "1q2w3e4r!", "죠르디", "jordy@kakaocorp.com");

        // when
        Response response = UserController.handle(requestMessage);
        User actual = DataBase.findUserById("jordy");

        // then
        assertThat(response).isInstanceOf(ResponseFound.class);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("POST 메서드로 사용자 정보를 받으면 사용자 정보를 디비에 저장하고 Found를 응답한다.")
    @Test
    void handle2() {
        // given
        String requestLine = "POST /user/create HTTP/1.1";
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Host", "localhost:8080");
        requestHeader.put("Connection", "keep-alive");
        requestHeader.put("Content-Length", "59");
        requestHeader.put("Content-Type", "application/x-www-form-urlencoded");
        requestHeader.put("Accept", "*/*");
        String requestBody = "userId=jordy&password=1q2w3e4r!&name=죠르디&email=jordy@kakaocorp.com";

        RequestMessage requestMessage = RequestMessage.of(requestLine, requestHeader, requestBody);
        User expected = User.of("jordy", "1q2w3e4r!", "죠르디", "jordy@kakaocorp.com");

        // when
        Response response = UserController.handle(requestMessage);
        User actual = DataBase.findUserById("jordy");

        // then
        assertThat(response).isInstanceOf(ResponseFound.class);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("POST 메서드로 로그인 요청시, 유효한 사용자라면 헤더에 로그인 성공을 담아 index.html로 이동한다.")
    @Test
    void login() {
        // given
        User expected = User.of("jordy", "1q2w3e4r!", "죠르디", "jordy@kakaocorp.com");
        DataBase.addUser(expected);

        String requestLine = "POST /user/login HTTP/1.1";
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Host", "localhost:8080");
        requestHeader.put("Connection", "keep-alive");
        requestHeader.put("Content-Length", "32");
        requestHeader.put("Content-Type", "application/x-www-form-urlencoded");
        requestHeader.put("Accept", "*/*");
        String requestBody = "userId=jordy&password=1q2w3e4r!";

        RequestMessage requestMessage = RequestMessage.of(requestLine, requestHeader, requestBody);

        // when
        Response response = UserController.handle(requestMessage);

        // then
        assertThat(response).isEqualTo(ResponseFound.of("/", true));
    }

    @DisplayName("POST 메서드로 로그인 요청시, 유효하지 않은 사용자라면 헤더에 로그인 실패를 담아 login_failed.html로 이동한다.")
    @Test
    void login2() {
        // given
        User expected = User.of("jordy", "1q2w3e4r!", "죠르디", "jordy@kakaocorp.com");
        DataBase.addUser(expected);

        String requestLine = "POST /user/login HTTP/1.1";
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Host", "localhost:8080");
        requestHeader.put("Connection", "keep-alive");
        requestHeader.put("Content-Length", "32");
        requestHeader.put("Content-Type", "application/x-www-form-urlencoded");
        requestHeader.put("Accept", "*/*");
        String requestBody = "userId=ryan&password=1q2w3e4r!";

        RequestMessage requestMessage = RequestMessage.of(requestLine, requestHeader, requestBody);

        // when
        Response response = UserController.handle(requestMessage);

        // then
        assertThat(response).isEqualTo(ResponseFound.of("/user/login_failed.html", false));
    }
}
