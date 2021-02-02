package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.HttpMethod;
import webserver.domain.HttpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestTest {
    private String testDirectory = "src/test/resources/";

    @DisplayName("GET Url Parameter")
    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeaders().get("Connection"));
        assertEquals("javajigi", request.getParameters().get("userId"));
    }

    @DisplayName("POST Body Parameter")
    @Test
    public void request_POST1() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST1.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeaders().get("Connection"));
        assertEquals("javajigi", request.getParameters().get("userId"));
    }

    @DisplayName("POST Body, Url Parameter")
    @Test
    public void request_POST2() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeaders().get("Connection"));
        assertEquals("1", request.getParameters().get("id"));
        assertEquals("javajigi", request.getParameters().get("userId"));
    }
}