package user;

import annotation.web.ResponseStatus;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static user.UserCreateAcceptanceTest.createUserRequest;

public class UserLoginAcceptanceTest extends AcceptanceTest {

    @DisplayName("유저가 로그인을 시도한다")
    @Test
    void userList() {
        String loginId = "loginId";
        String password = "password";
        String createValues1 = String.format("userId=%s&password=%s&name=%s&email=%s",
                loginId, password, "loginName", "login@email.com");

        createUserRequest(createValues1);

        String requestBody = String.format("userId=%s&password=%s", loginId, password);
        ExtractableResponse<Response> response = loginRequest(requestBody);

        loginSuccess(response);
    }

    public static ExtractableResponse<Response> loginRequest(String body) {
        return RestAssured
                .given().log().all()
                .body(body)
                .when().post("/user/login")
                .then().log().all()
                .extract();
    }

    private void loginSuccess(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(ResponseStatus.FOUND.getStatusCode());
        assertThat(response.header("Location")).isEqualTo("/index.html");
        assertThat(response.header("Set-Cookie")).contains("logined=true");
    }


}
