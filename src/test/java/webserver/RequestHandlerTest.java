package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {

    private RequestHandler requestHandler;
    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        requestHandler = new RequestHandler(null, null, new ResponseHandler() {
            @Override
            Template getTemplate(Response response) throws IOException {
                return new Handlebars().compileInline("{{this}}");
            }
        });
        out = new ByteArrayOutputStream();
    }

    @Test
    void seeOther() throws IOException {
        Response response = new Response();
        response.setStatus(ResponseStatus.SEE_OTHER);
        response.setHeaders("Location: /index.html");
        requestHandler.response(new DataOutputStream(out), response);

        assertThat(out.toString()).isEqualTo("HTTP/1.1 302 Found \r\n" +
                                             "Location: /index.html\r\n\r\n");
    }

    @Test
    void ok() throws IOException {
        Response response = new Response();
        String body = "Hello World!";
        response.setBody(body.getBytes());
        requestHandler.response(new DataOutputStream(out), response);

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
        requestHandler.response(new DataOutputStream(out), response);

        assertThat(out.toString()).isEqualTo("HTTP/1.1 200 OK \r\n" +
                                             "Content-Length: " + message.length() + "\r\n\r\n" +
                                             message);
    }
}
