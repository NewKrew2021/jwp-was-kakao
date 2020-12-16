package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RequestUtil 테스트")
public class RequestParamParserTest {
    @Test
    public void encode() throws UnsupportedEncodingException {
        String email = "56%40gmail.com";
        assertThat(URLDecoder.decode(email, "UTF-8")).isEqualTo("56@gmail.com");
    }

    @Test
    public void getRequestParamFromBody() {
        Map<String, String> requestParam = RequestParamParser.parseRequestParams("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        assertThat(requestParam.get("userId")).isEqualTo("javajigi");
        assertThat(requestParam.get("password")).isEqualTo("password");
        assertThat(requestParam.get("name")).isEqualTo("박재성");
        assertThat(requestParam.get("email")).isEqualTo("javajigi@slipp.net");
    }
}