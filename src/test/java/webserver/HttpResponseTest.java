package webserver;

import org.junit.jupiter.api.Test;
import webserver.domain.HttpHeader;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;
import webserver.exceptions.RequiredHeaderNotFoundException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class HttpResponseTest {
    private String testDirectory = "src/test/resources/";

    @Test
    public void responseForwardBody() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_ForwardBody.txt"));
        response.send(HttpStatusCode.OK, "Hello World");
    }

    @Test
    public void responseRedirect_Normal() throws Exception {
        // Http_Redirect.txt 결과는 응답 headere에 Location 정보가 /index.html로 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect_normal.txt"));
        response.getHeaders().add(HttpHeader.LOCATION, "/index.html");
        response.send(HttpStatusCode.FOUND);
    }

    @Test
    public void responseRedirect_Abnormal() throws Exception {
        // Header 에 Location 를 명시하지않고 302 코드를 보낼 수는 없다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect_Abnormal_Empty.txt"));
        assertThatExceptionOfType(RequiredHeaderNotFoundException.class)
                .isThrownBy(() -> response.send(HttpStatusCode.FOUND));
    }

    @Test
    public void responseCookies() throws Exception {
        // Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.getHeaders().add(HttpHeader.SET_COOKIE, "logined=true");
        response.getHeaders().add(HttpHeader.LOCATION, "/index.html");
        response.send(HttpStatusCode.FOUND);
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(testDirectory + filename);
    }
}
