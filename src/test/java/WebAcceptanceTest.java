import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import user.model.User;
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

        String id = "javajigi";
        String password = "password";
        String name = "javajigi";
        String email = "javajigi@javajigi.com";
        user = new User(id, password, name, email);
    }

    @DisplayName("GET /index.html")
    @Test
    void index() {
        ExtractableResponse<Response> response = RestAssured.given().log().all().
                when().get("/index.html").
                then().log().all().
                statusCode(HttpStatus.OK.value()).
                extract();
//        assertThat(response.body().asByteArray()).isEqualTo(indexBody);
    }

    @DisplayName("GET /")
    @Test
    void index2() {
        ExtractableResponse<Response> response = RestAssured.given().log().all().
                when().get("/").
                then().log().all().
                statusCode(HttpStatus.OK.value()).
                extract();
//        assertThat(response.body().asByteArray()).isEqualTo(indexBody);
    }

    @DisplayName("GET /user/create")
    @Test
    void signUpAndRedirection_GET() {
        String path = "/user/create";

        ExtractableResponse<Response> response = 회원가입_GET(path, user);

//        assertThat(response.body().asByteArray()).isEqualTo(indexBody);
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.FOUND.value());
    }

    @DisplayName("POST /user/create")
    @Test
    void signUpAndRedirection_POST() {
        String path = "/user/create";

        ExtractableResponse<Response> response = 회원가입_POST(path, user);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FOUND.value());
    }

    @DisplayName("POST /user/login success")
    @Test
    void loginSuccess() {
        String path = "/user/login";

        회원가입_POST(path, user);

        String id = user.getId();
        String password = user.getPassword();

        ExtractableResponse<Response> response = 로그인_요청(path, id, password);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(response.cookies().get("logined")).isEqualTo("true");
    }

    @DisplayName("POST /user/login fail")
    @Test
    void loginFail() {
        String path = "/user/login";

        회원가입_POST(path, user);

        String id = "NotUser";
        String password = "NotUser";

        ExtractableResponse<Response> response = 로그인_요청(path, id, password);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(response.cookies().get("logined")).isEqualTo("false");
    }

    @DisplayName("GET /user/list success")
    @Test
    void userListSuccess() {
        String loginPath = "/user/login";
        String path = "/user/list";
        회원가입_POST(loginPath, user);
        ExtractableResponse<Response> loginResponse = 로그인_요청(loginPath, user.getId(), user.getPassword());
        ExtractableResponse<Response> response = 사용자_리스트_요청(path, loginResponse.cookie("logined"));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.header("Content-Location")).isEqualTo("/user/list");
    }

    @DisplayName("GET /user/list fail")
    @Test
    void userListFail() {
        String loginPath = "/user/login";
        String path = "/user/list";
        회원가입_POST(loginPath, user);
        ExtractableResponse<Response> loginResponse = 로그인_요청(loginPath, "NotUser", "NotUser");
        ExtractableResponse<Response> response = 사용자_리스트_요청(path, loginResponse.cookie("logined"));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.header("Content-Location")).isEqualTo("/user/login.html");
    }

    private ExtractableResponse<Response> 사용자_리스트_요청(String path, String logined) {
        return RestAssured.
                given().log().all().cookie("logined", logined).
                when().get(path).
                then().log().all().
                statusCode(HttpStatus.OK.value()).
                extract();
    }

    private ExtractableResponse<Response> 로그인_요청(String path, String id, String password) {
        return RestAssured.
                given().log().all().
                param("userId", id).
                param("password", password).
                when().post(path).
                then().log().all().
                extract();
    }

    ExtractableResponse<Response> 회원가입_GET(String path, User user) {
        return RestAssured.
                given().log().all().
                param("userId", user.getId()).
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
                param("userId", user.getId()).
                param("password", user.getPassword()).
                param("name", user.getName()).
                param("email", user.getEmail()).
                when().post(path).
                then().log().all().
                extract();
    }
}
