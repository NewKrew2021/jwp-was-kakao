package webserver;

import http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";
    private DataOutputStream dos;
    private ByteArrayOutputStream bos;

    @BeforeEach
    void init(){
        bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);
    }

    @DisplayName("body를 전달한다.")
    @Test
    public void responseForward() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        HttpResponse response = new HttpResponse(dos);
        response.forward(body);

        String expected = readResponseFile("Http_Forward.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @DisplayName("/index.html로 redirect한다.")
    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(dos);
        response.sendRedirect("/index.html");

        String expected = readResponseFile("Http_Redirect.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @DisplayName("set-cookie 헤더를 설정하여 응답한다.")
    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(dos);
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");

        String expected = readResponseFile("Http_Cookie.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    private String readResponseFile(String filename) throws IOException {
        InputStream in = new FileInputStream(testDirectory + filename);
        return new String(in.readAllBytes());
    }
}
