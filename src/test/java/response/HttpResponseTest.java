package response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.")
    public void responseForward() throws Exception {
        HttpResponse response = HttpResponse.of(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");

        BufferedReader reader = createBufferedReader("Http_Forward.txt");
        assertThat(reader.lines().filter(line -> line.contains("SLiPP Java Web Programming")).count()).isGreaterThanOrEqualTo(1L);
    }

    @Test
    @DisplayName("Http_Redirect.txt 결과는 응답 headere에 Location 정보가 /index.html로 포함되어 있어야 한다.")
    public void responseRedirect() throws Exception {
        HttpResponse response = HttpResponse.of(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");

        BufferedReader reader = createBufferedReader("Http_Redirect.txt");
        assertThat(reader.lines().filter(line -> line.contains("Location: /index.html")).count()).isGreaterThanOrEqualTo(1L);
    }

    @Test
    @DisplayName("Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.")
    public void responseCookies() throws Exception {
        HttpResponse response = HttpResponse.of(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");

        BufferedReader reader = createBufferedReader("Http_Cookie.txt");
        assertThat(reader.lines().filter(line -> line.contains("logined=true")).count()).isGreaterThanOrEqualTo(1L);
    }

    private DataOutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new DataOutputStream(new FileOutputStream(new File(testDirectory + filename)));
    }

    private BufferedReader createBufferedReader(String filename) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(new File(testDirectory + filename))));
    }

}