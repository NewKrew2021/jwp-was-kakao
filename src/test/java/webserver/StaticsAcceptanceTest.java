package webserver;

import annotation.web.ResponseStatus;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class StaticsAcceptanceTest extends AcceptanceTest {
    @DisplayName("static 파일이 알맞게 오는지 확인")
    @ParameterizedTest
    @CsvSource({"/index.html,text/html",
            "user/login.html,text/html",
            "/css/styles.css,text/css",
            "/fonts/glyphicons-halflings-regular.ttf,font/ttf",
            "/favicon.ico,image/png",
            "/js/scripts.js,text/javascript",
            "/images/80-text.png,image/png"})
    void testGetStatics(String templatePath, String expected) {
        ExtractableResponse<Response> response = requestView(templatePath);

        respondedWith(response, expected);
    }

    private ExtractableResponse<Response> requestView(String templatePath) {
        return RestAssured
                .given().log().all()
                .when().get(templatePath)
                .then().log().all()
                .extract();
    }

    private void respondedWith(ExtractableResponse<Response> response, String expected) {
        assertThat(response.statusCode()).isEqualTo(ResponseStatus.OK.getStatusCode());
        assertThat(response.contentType()).isEqualTo(expected);
    }
}
