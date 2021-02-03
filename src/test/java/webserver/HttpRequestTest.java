package webserver;

import domain.HttpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        HttpRequest request = new HttpRequest(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/user/create", request.getUrl());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");
        HttpRequest request = new HttpRequest(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getUrl());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }
}
