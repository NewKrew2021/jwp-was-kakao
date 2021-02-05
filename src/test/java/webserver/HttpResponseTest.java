package webserver;

import http.HttpHeader;
import http.HttpRequest;
import http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.*;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        InputStream in = new FileInputStream(new File(testDirectory + "http_redirect_request.txt"));
        HttpRequest request = new HttpRequest(in);

        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"), request.getHttpHeader());
        response.forwardStatic("./templates/index.html");
    }

    @Test
    public void responseRedirect() throws Exception {
        // Http_Redirect.txt 결과는 응답 header에 Location 정보가 /index.html로 포함되어 있어야 한다.
        InputStream in = new FileInputStream(new File(testDirectory + "http_redirect_request.txt"));
        HttpRequest request = new HttpRequest(in);

        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"), request.getHttpHeader());
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseCookies() throws Exception {
        // Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.
        InputStream in = new FileInputStream(new File(testDirectory + "http_cookie_request.txt"));
        HttpRequest request = new HttpRequest(in);

        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"), request.getHttpHeader());
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
