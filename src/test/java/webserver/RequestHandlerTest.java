package webserver;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestHandlerTest {
    private RequestHandler requestHandler;

    @Test
    void parseArgument() throws UnsupportedEncodingException {
        requestHandler = new RequestHandler();

        String argumentText = "userId=test_id&password=test_pw&name=test_name&email=test_email";
        Map<String, String> actual = requestHandler.parseArgument(argumentText);

        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "test_id");
        expected.put("password", "test_pw");
        expected.put("name", "test_name");
        expected.put("email", "test_email");

        파싱_결과_체크(actual, expected);
    }

    private void 파싱_결과_체크(Map<String, String> actual, Map<String, String> expected){
        assertThat(actual.get("userId")).isEqualTo(expected.get("userId"));
        assertThat(actual.get("password")).isEqualTo(expected.get("password"));
        assertThat(actual.get("name")).isEqualTo(expected.get("name"));
        assertThat(actual.get("email")).isEqualTo(expected.get("email"));
    }

}