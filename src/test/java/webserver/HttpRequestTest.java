package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        HttpRequest httpRequest = new HttpRequest(br);

        assertEquals("GET", httpRequest.getMethod());
        assertEquals("/user/create", httpRequest.getPath());
        assertEquals("keep-alive", httpRequest.getHeader("Connection"));
        assertEquals("javajigi", httpRequest.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        HttpRequest httpRequest = new HttpRequest(br);

        assertEquals("POST", httpRequest.getMethod());
        assertEquals("/user/create", httpRequest.getPath());
        assertEquals("keep-alive", httpRequest.getHeader("Connection"));
        assertEquals("javajigi", httpRequest.getParameter("userId"));
    }

    @Test
    public void request_POST2() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        HttpRequest httpRequest = new HttpRequest(br);

        assertEquals("POST", httpRequest.getMethod());
        assertEquals("/user/create", httpRequest.getPath());
        assertEquals("keep-alive", httpRequest.getHeader("Connection"));
        assertEquals("1", httpRequest.getParameter("id"));
        assertEquals("javajigi", httpRequest.getParameter("userId"));
    }
}
