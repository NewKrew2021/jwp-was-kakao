package webserver;

import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("Http_ForwardBody.txt 에는 200, Hello World 가 있어야 한다")
    @Test
    public void responseSend() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_ForwardBody.txt"));
        response.send(HttpStatusCode.OK, "Hello World");
    }

    @DisplayName("Http_Redirect_normal.txt 에는 302, Location 정보가 /index.html로 포함되어 있어야 한다")
    @Test
    public void responseRedirect_Normal() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect_normal.txt"));
        response.getHeaders().add(HttpHeader.LOCATION, "/index.html");
        response.send(HttpStatusCode.FOUND);
    }

    @DisplayName("Http_Redirect_Abnormal_Empty.txt 는 비어있고, Header 에 Location 없는 302 응답은 에러를 뱉어야 한다")
    @Test
    public void responseRedirect_Abnormal() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect_Abnormal_Empty.txt"));
        assertThatExceptionOfType(RequiredHeaderNotFoundException.class)
                .isThrownBy(() -> response.send(HttpStatusCode.FOUND));
    }

    @DisplayName("Http_Cookies.txt 는 302, Set-Cookie 값으로 logined=true 가 있어야 한다")
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
