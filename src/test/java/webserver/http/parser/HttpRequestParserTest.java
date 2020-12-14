package webserver.http.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestParserTest {

    private final String resourceDirectory = "src/test/resources/";

    @Test
    @DisplayName("루트 주소로 Get 요청을 보냄")
    void isGetMethod() throws Exception {
        InputStream in = new FileInputStream(new File(resourceDirectory + "GET_ROOT_TEST"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest httpRequest = HttpRequestParser.fromReader(bufferedReader);
        assertThat(httpRequest.isMethod(HttpMethod.GET)).isTrue();
    }
    @Test
    @DisplayName("루트 주소로 POST 요청을 보냄")
    void isPostMethod() throws Exception {
        InputStream in = new FileInputStream(new File(resourceDirectory + "POST_ROOT_TEST"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest httpRequest = HttpRequestParser.fromReader(bufferedReader);
        assertThat(httpRequest.isMethod(HttpMethod.POST)).isTrue();
        assertThat(httpRequest.getParam("param1")).isEqualTo("paramValue1");
    }

}
