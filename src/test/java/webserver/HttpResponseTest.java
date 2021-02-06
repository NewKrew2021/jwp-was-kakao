package webserver;

import http.HttpRequest;
import http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseTest {
    private final String testDirectory = "./src/test/resources/";

    @Test
    public void request_POST2() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST2.txt");
        HttpRequest request = HttpRequest.of(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getUri());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("1", request.getParameter("id"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void responseForward() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = HttpResponse.of(createOutputStream("Http_Forward.txt"));
        response.forward("./templates/index.html");
    }

    @Test
    public void responseRedirect() throws Exception {
        // Http_Redirect.txt 결과는 응답 headere에 Location 정보가 /index.html로 포함되어 있어야 한다.
        HttpResponse response = HttpResponse.of(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseCookies() throws Exception {
        // Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.
        HttpResponse response = HttpResponse.of(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(testDirectory + filename);
    }
}
