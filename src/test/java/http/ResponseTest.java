package http;

import http.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Response 클래스")
public class ResponseTest {

    @DisplayName("파일을 전송하는 기능 테스트")
    @Test
    public void ofDefaultFileTest() throws IOException, URISyntaxException {
        //given
        byte[] testFile = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");

        //when
        Response response = Response.ofDefaultFile("/user/login.html");

        //then
        assertThat(response.toString())
                .contains("HTTP/1.1 200 OK")
                .contains("Content-Type: text/html;charset=utf-8")
                .contains(new String(testFile, StandardCharsets.UTF_8));
    }

    @DisplayName("redirect response 테스트")
    @Test
    public void ofRedirectTest() {
        //given
        String redirectUrl = "/index.html";

        //when
        Response response = Response.ofRedirect(redirectUrl);

        //then
        assertThat(response.toString())
                .contains("HTTP/1.1 302 Found")
                .contains("Content-Type: HTML;charset=utf-8")
                .contains("Location: /index.html");
    }

    /*
    @DisplayName("html 페이지 생성")
    @Test
    public void ofDynamicHtmlTest() throws IOException, URISyntaxException {
        //given
        byte[] testFile = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");
        ResponseBody body = new ResponseBody(testFile);

        //when

    }
     */
}
