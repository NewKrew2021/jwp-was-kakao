package webserver.user;

import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private final String testDirectory = "src/test/resources-test/";

    @Test
    void GET_CREATE_USER_PARAMETER_TEST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_HTTP_CREATE_USER"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest request = new HttpRequest(bufferedReader);
        assertThat(request.getBody().getQueryString().getParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getBody().getQueryString().getParameter("password")).isEqualTo("password");
        assertThat(request.getBody().getQueryString().getParameter("name")).isEqualTo("박재성");
        assertThat(request.getBody().getQueryString().getParameter("email")).isEqualTo("javajigi@slipp.net");

    }

    @Test
    void POST_CREATE_USER_BODY_TEST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "POST_HTTP_CREATE_USER"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest request = new HttpRequest(bufferedReader);
        assertThat(request.getBody().getParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getBody().getParameter("password")).isEqualTo("password");
        assertThat(request.getBody().getParameter("name")).isEqualTo("박재성");
        assertThat(request.getBody().getParameter("email")).isEqualTo("javajigi@slipp.net");
    }
}
