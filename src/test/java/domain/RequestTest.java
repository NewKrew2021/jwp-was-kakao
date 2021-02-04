package domain;

import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Request 클래스")
public class RequestTest {
    List<String> testStrings = Arrays.asList(
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1"
            , "Host: localhost:8080"
            , "Connection: keep-alive"
            , "Accept: */*"
            , ""
            , ""
    );

    @DisplayName("urlPath 확인")
    @Test
    public void getUrlPathTest() {
        Request request = new Request(testStrings);
        String urlPath = request.getUrlPath();
        assertThat(urlPath).isEqualTo("/user/create");
    }

    @DisplayName("method 확인")
    @Test
    public void getMethodTest() {
        Request request = new Request(testStrings);
        String urlPath = request.getMethod();
        assertThat(urlPath).isEqualTo("GET");
    }

    @DisplayName("queries 확인")
    @Test
    public void getQueriesTest() {
        Request request = new Request(testStrings);
        Map<String, String> queries = request.getQueries();
        assertThat(queries.get("userId")).isEqualTo("javajigi");
        assertThat(queries.get("password")).isEqualTo("password");
        assertThat(queries.get("name")).isEqualTo("박재성");
        assertThat(queries.get("email")).isEqualTo("javajigi@slipp.net");
    }
}
