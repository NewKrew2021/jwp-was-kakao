import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class WebAcceptanceTest {
    byte[] indexBody;
    User user;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException {
        indexBody = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        String userId = "javajigi";
        String password = "password";
        String name = "javajigi";
        String email = "javajigi@javajigi.com";
        user = new User(userId, password, name, email);
    }

    @DisplayName("GET /index.html")
    @Test
    void index() {
        ExtractableResponse<Response> response = RestAssured.given().log().all().
                when().get("/index.html").
                then().log().all().
                statusCode(HttpStatus.OK.value()).
                extract();
        assertThat(response.body().asByteArray()).isEqualTo(indexBody);
    }

    @DisplayName("GET /")
    @Test
    void index2() {
        ExtractableResponse<Response> response = RestAssured.given().log().all().
                when().get("/").
                then().log().all().
                statusCode(HttpStatus.OK.value()).
                extract();
        assertThat(response.body().asByteArray()).isEqualTo(indexBody);
    }

    @DisplayName("GET /user/form.html")
    @Test
    void signUpAndRedirection_GET() {
        String path = "/user/create";

        ExtractableResponse<Response> response = 회원가입_GET(path, user);

        assertThat(response.body().asByteArray()).isEqualTo(indexBody);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("POST /user/form.html")
    @Test
    void signUpAndRedirection_POST() {
        String path = "/user/create";

        ExtractableResponse<Response> response = 회원가입_POST(path, user);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FOUND.value());
    }

    @DisplayName("POST /user/login")
    @Test
    void loginSuccess() {
        String path = "/user/login";

        회원가입_POST(path, user);

        String userId = user.getUserId();
        String password = user.getPassword();

        ExtractableResponse<Response> response = 로그인요청(path, userId, password);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.cookies().get("logined")).isEqualTo("true");
    }

    @DisplayName("POST /user/login")
    @Test
    void loginFail() {
        String path = "/user/login";

        회원가입_POST(path, user);

        String userId = "NotUser";
        String password = "NotUser";

        ExtractableResponse<Response> response = 로그인요청(path, userId, password);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.cookies().get("logined")).isEqualTo("false");
    }

    private ExtractableResponse<Response> 로그인요청(String path, String userId, String password) {
        return RestAssured.
                given().log().all().
                param("userId", userId).
                param("password", password).
                when().post(path).
                then().log().all().
                statusCode(HttpStatus.OK.value()).
                extract();
    }

    ExtractableResponse<Response> 회원가입_GET(String path, User user) {
        return RestAssured.
                given().log().all().
                param("userId", user.getUserId()).
                param("password", user.getPassword()).
                param("name", user.getName()).
                param("email", user.getEmail()).
                when().get(path).
                then().log().all().
                extract();
    }

    ExtractableResponse<Response> 회원가입_POST(String path, User user) {
        return RestAssured.
                given().log().all().
                param("userId", user.getUserId()).
                param("password", user.getPassword()).
                param("name", user.getName()).
                param("email", user.getEmail()).
                when().post(path).
                then().log().all().
                extract();
    }
}
