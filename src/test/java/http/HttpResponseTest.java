package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    public static final String PATH = "./src/test/resources/";
    private HttpResponse response;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        response = new HttpResponse.Builder()
                .status(HttpStatus.OK)
                .contentType("text/html;charset=utf-8")
                .body("./templates/index.html")
                .cookie(new Cookie("logined", "true"))
                .build();
    }

    @DisplayName("HttpResponse가 올바르게 생성되는지 테스트")
    @Test
    void create() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        String headers = "Content-Length: " + body.length + "\r\n" +
                "Content-Type: text/html;charset=utf-8\r\n" +
                "Set-Cookie: logined=true\r\n";

        assertThat(response.getHttpStatus()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(response.getBody()).isEqualTo(body);
        assertThat(response.headersToString()).isEqualTo(headers);
    }

    @DisplayName("DataOutputStream이 올바르게 생성되는지 테스트")
    @Test
    void sendResponse() throws IOException {
        String expected = response.getHttpStatus() + " \r\n" +
                response.headersToString() +
                "\r\n" +
                new String(response.getBody());

        DataOutputStream dos = new DataOutputStream(new FileOutputStream(PATH + "output-stream.txt"));
        response.sendResponse(dos);
        String actual = new String(Files.readAllBytes(Paths.get(PATH + "output-stream.txt")));

        assertThat(expected).isEqualTo(actual);
    }
}