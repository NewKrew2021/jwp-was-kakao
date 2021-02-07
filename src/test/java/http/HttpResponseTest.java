package http;

import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");

        Path path = Paths.get(testDirectory + "Http_Forward.txt");
        byte[] result = Files.readAllBytes(path);
        assertThat(result).contains(FileIoUtils.loadFileFromClasspath("templates/index.html"));
    }


    @Test
    public void responseRedirect() throws Exception {
        // Http_Redirect.txt 결과는 응답 header에 Location 정보가 /index.html로 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");

        Path path = Paths.get(testDirectory + "Http_Redirect.txt");
        byte[] result = Files.readAllBytes(path);
        assertThat(result).contains("Location: http://localhost:8080/index.html".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");

        Path path = Paths.get(testDirectory + "Http_Cookie.txt");
        byte[] result = Files.readAllBytes(path);
        assertThat(result).contains("Set-Cookie: logined=true".getBytes(StandardCharsets.UTF_8));
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }

}