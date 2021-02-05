package framework.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("POST 메서드를 요청했을 때 method, path, 헤더, 바디를 확인한다")
    public void request_POST() throws Exception {
        HttpRequest request = HttpRequest.of(createInputStream("Http_POST.txt"));

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeaders().get("Connection"));
        assertEquals("brody", request.getParameters().get("userId"));
        assertEquals("1234", request.getParameters().get("password"));
        assertEquals("john", request.getParameters().get("name"));
        assertEquals("abc@abc.com", request.getParameters().get("email"));
    }

    @Test
    @DisplayName("GET 메서드를 요청했을 때 method, 헤더 확인한다")
    public void request_GET() throws Exception {
        HttpRequest request = HttpRequest.of(createInputStream("Http_GET.txt"));

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/index.html", request.getPath());
        assertEquals("keep-alive", request.getHeaders().get("Connection"));
    }

    private InputStream createInputStream(String filename) throws FileNotFoundException {
        return new FileInputStream(new File(testDirectory + filename));
    }
}