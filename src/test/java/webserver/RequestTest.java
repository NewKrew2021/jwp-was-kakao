package webserver;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");
        Request request = Request.of(in);

        assertEquals(request.getMethod(), "POST");
        assertEquals(request.getUri(), "/user/create");
        assertEquals(request.getHeader("Connection"), "keep-alive");
    }

    @Test
    void getMethodTest() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        Request request = Request.of(in);
        assertEquals(request.getMethod(), "GET");
    }

    @Test
    void getUriTest() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        Request request = Request.of(in);
        assertEquals(request.getUri(), "/index.html");
    }

    @Test
    void getUriWithParametersTest() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET_Simple_Value.txt");
        Request request = Request.of(in);
        assertEquals(request.getUri(), "/index.html");
        assertEquals(request.getParameter("param"), "1234");
    }

}
