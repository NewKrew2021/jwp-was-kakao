package webserver.http;

import com.google.common.base.Charsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    HttpResponse httpResponse;
    StringOutputStream out;

    @BeforeEach
    void setUp(){
        out = new StringOutputStream();
        httpResponse = mockHttpResponse(out);
    }

    @DisplayName("body 를 설정하면 Content-Length 해더가 추가된다")
    @Test
    void setBody(){
        httpResponse.setBody("abcdefg".getBytes());

        assertThat(httpResponse.getHeaders()
                .stream()
                .filter(it -> "Content-Length".equalsIgnoreCase(it.getKey()))
                .findFirst()
                .get()
                .getValue()).isEqualTo("7");
    }

    @DisplayName("setContentType() 을 호출하면 Content-Type 해더가 추가된다")
    @Test
    void setContentType(){
        httpResponse.setContentType(MimeType.TEXT_HTML, Charsets.UTF_8);

        assertThat(httpResponse.getHeaders()
                .stream()
                .filter(it -> "Content-Type".equalsIgnoreCase(it.getKey()))
                .findFirst()
                .get()
                .getValue()).isEqualTo("text/html; UTF-8");

    }

    @DisplayName("redirect 시 http status 302 로 응답하고 Location header 가 추가된다")
    @Test
    void redirect() {
        httpResponse.sendRedirect("/index.html");

        assertThat(out.toString()).isEqualTo(
                "HTTP/1.1 302 Found \r\n" +
                        "Location: /index.html\r\n\r\n");
    }

    @DisplayName("redirect 시 header 를 추가 할 수 있다")
    @Test
    void redirectWithCustomHeader() {
        httpResponse.sendRedirect("/index.html", Arrays.asList(new HttpHeader("ServerVersion", "1.0")));

        assertThat(out.toString()).isEqualTo(
                "HTTP/1.1 302 Found \r\n" +
                        "Location: /index.html\r\n" +
                        "ServerVersion: 1.0\r\n\r\n");
    }

    private HttpResponse mockHttpResponse(OutputStream out) {
        return new HttpResponse(out);
    }

    private class StringOutputStream extends OutputStream {
        private StringBuilder sb = new StringBuilder();

        @Override
        public void write(int b) throws IOException {
            sb.append((char) b);
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }

}