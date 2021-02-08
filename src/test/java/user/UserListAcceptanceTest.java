package user;

import annotation.web.ResponseStatus;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static user.UserCreateAcceptanceTest.createUserRequest;

public class UserListAcceptanceTest extends AcceptanceTest {
    private final String USER_ID_1 = "testId1";
    private final String USER_ID_2 = "testId2";
    private final String USER_NAME_1 = "testName1";
    private final String USER_NAME_2 = "testName2";
    private final String USER_EMAIL_1 = "test1@email.com";
    private final String USER_EMAIL_2 = "test2@email.com";

    @DisplayName("유저 목록을 가져온다")
    @Test
    void userList() {
        String createValues1 = String.format("userId=%s&password=%s&name=%s&email=%s",
                USER_ID_1, "password", USER_NAME_1, USER_EMAIL_1);
        String createValues2 = String.format("userId=%s&password=%s&name=%s&email=%s",
                USER_ID_2, "password", USER_NAME_2, USER_EMAIL_2);

        createUserRequest(createValues1);
        createUserRequest(createValues2);

        ExtractableResponse<Response> response = getUserListRequest();

        userListObtained(response);
    }

    public static ExtractableResponse<Response> getUserListRequest() {
        return RestAssured
                .given().log().all()
                .header(new Header("Cookie", "logined=true; Path=/"))
                .when().get("/user/list")
                .then().log().all()
                .extract();
    }

    private void userListObtained(ExtractableResponse<Response> response) {
        String body = response.body().asString();

        assertThat(response.statusCode()).isEqualTo(ResponseStatus.OK.getStatusCode());
        assertThat(body).contains(USER_ID_1, USER_ID_2, USER_NAME_1, USER_NAME_2, USER_EMAIL_1, USER_EMAIL_2);
    }
}