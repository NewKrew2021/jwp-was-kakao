package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;


class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("html 리스폰스 포워드 테스트")
    public void responseForward() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(new DataOutputStream(createOutputStream("Http_Forward.txt")));
        response.forward("./templates/index.html");
        assertThat(response.getHeaders().get("Content-Type")).isEqualTo("text/html; charset=utf-8");
    }

    @Test
    @DisplayName("css 리스프노스 포워드 테스트")
    void responseCssForward() throws Exception {
        HttpResponse response = new HttpResponse(new DataOutputStream(createOutputStream("Css_Forward.txt")));
        response.forward("./static/css/styles.css");
        assertThat(response.getHeaders().get("Content-Type")).isEqualTo("text/css; charset=utf-8");
    }

    @Test
    @DisplayName("리스폰스 리다이렉트 테스트")
    public void responseRedirect() throws Exception {
        // Http_Redirect.txt 결과는 응답 header에 Location 정보가 /index.html로 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(new DataOutputStream(createOutputStream("Http_Redirect.txt")));
        response.sendRedirect("/index.html");
        assertThat(response.getHeaders().get("Location")).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("쿠키가 생성여부 테스트")
    public void responseCookies() throws Exception {
        // Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(new DataOutputStream(createOutputStream("Http_Cookie.txt")));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
        assertThat(response.getHeaders().get("Set-Cookie")).isEqualTo("logined=true");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(testDirectory + filename);
    }
}