package webserver;

import http.HttpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    private final String testDirectory = "./src/test/resources/";

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");
        HttpRequest request = HttpRequest.of(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getUri());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("1234", request.getParameter("userId"));
        assertEquals("1234", request.getParameter("password"));
        assertEquals("박재성", request.getParameter("name"));
        assertEquals("email@email.com", request.getParameter("email"));
    }

    @Test
    void getMethodTest() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        HttpRequest request = HttpRequest.of(in);
        assertEquals(HttpMethod.GET, request.getMethod());
    }

    @Test
    void getUriTest() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        HttpRequest request = HttpRequest.of(in);
        assertEquals("/index.html", request.getUri());
    }

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
