package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private final String testDirectory = "./src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("./templates/index.html");

        String lines = readFile(testDirectory + "/Http_Forward.txt");
        String expected = readFile("./src/main/resources/templates/index.html");

        assertForward(lines, expected);
    }

    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");

        String lines = readFile(testDirectory + "/Http_Redirect.txt");

        assertRedirectLocation(lines, "/index.html");
    }

    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true; Path=/");
        response.sendRedirect("/index.html");

        String lines = readFile(testDirectory + "/Http_Cookie.txt");
        assertLoginedIsTrue(lines);
        assertRedirectLocation(lines, "/index.html");
    }

    private void assertForward(String lines, String expected) {
        assertThat(lines.contains("200 OK")).isTrue();
        assertThat(lines.contains(expected)).isTrue();
    }

    private void assertLoginedIsTrue(String lines) {
        assertThat(lines.contains("logined=true")).isTrue();
    }

    private void assertRedirectLocation(String lines, String expected) {
        assertThat(lines.contains("302 Found")).isTrue();
        assertThat(lines.contains("Location: " + expected)).isTrue();
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(testDirectory + filename);
    }

    private String readFile(String path) {
        File file = new File(path);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines().collect(Collectors.joining("\r\n"));
        } catch (IOException e) {
            throw new IllegalArgumentException("Incorrect filename");
        }
    }
}
