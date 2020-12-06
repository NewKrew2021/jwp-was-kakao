package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.http.Response;
import webserver.http.ResponseStatus;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHandlerTest {

    private ByteArrayOutputStream out;
    private ResponseHandler responseHandler;

    @BeforeEach
    void setUp() {
        responseHandler = new ResponseHandler() {
            @Override
            Template getTemplate(Response response) throws IOException {
                return new Handlebars().compileInline("{{this}}");
            }
        };
        out = new ByteArrayOutputStream();
    }

    @Test
    void seeOther() throws IOException {
        Response response = new Response();
        response.setStatus(ResponseStatus.SEE_OTHER);
        response.setHeaders("Location: /index.html");
        responseHandler.handleResponse(new DataOutputStream(out), response);

        assertThat(out.toString()).isEqualTo("HTTP/1.1 302 Found \r\n" +
                                             "Location: /index.html\r\n\r\n");
    }

    @Test
    void ok() throws IOException {
        Response response = new Response();
        String body = "Hello World!";
        response.setBody(body.getBytes());
        responseHandler.handleResponse(new DataOutputStream(out), response);

        assertThat(out.toString()).isEqualTo("HTTP/1.1 200 OK \r\n" +
                                             "Content-Length: " + body.length() + "\r\n\r\n" +
                                             body);
    }

    @Test
    void ok_with_template() throws IOException {
        Response response = new Response();
        String message = "Hello World!";
        response.setModel(message);
        response.setViewName("/hello.html");
        responseHandler.handleResponse(new DataOutputStream(out), response);

        assertThat(out.toString()).isEqualTo("HTTP/1.1 200 OK \r\n" +
                                             "Content-Length: " + message.length() + "\r\n\r\n" +
                                             message);
    }
}
