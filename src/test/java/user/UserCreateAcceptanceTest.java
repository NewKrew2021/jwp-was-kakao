package user;

import annotation.web.ResponseStatus;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateAcceptanceTest extends AcceptanceTest {
    @DisplayName("user가 성공적으로 생성되었다")
    @Test
    void testCreate() {
        String userCreateValues = String.format("userId=%s&password=%s&name=%s&email=%s",
                "createId", "password", "createName", "create@email.com");

        ExtractableResponse<Response> response = createUserRequest(userCreateValues);

        createSuccess(response);
    }

    @DisplayName("중복된 userId가 이미 존재한다")
    @Test
    void userCreationFailure() {
        String userCreateValues = String.format("userId=%s&password=%s&name=%s&email=%s",
                "failureId", "password", "failureName", "failure@email.com");

        createUserRequest(userCreateValues);
        ExtractableResponse<Response> response = createUserRequest(userCreateValues);

        creationFailure(response);
    }

    public static ExtractableResponse<Response> createUserRequest(String body) {
        return RestAssured
                .given().log().all()
                .body(body)
                .when().post("/user/create")
                .then().log().all()
                .extract();
    }

    private void createSuccess(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(ResponseStatus.FOUND.getStatusCode());
        assertThat(response.header("Location")).isEqualTo("/index.html");
    }

    private void creationFailure(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(ResponseStatus.FOUND.getStatusCode());
        assertThat(response.header("Location")).isEqualTo("/user/form.html");
    }
}
