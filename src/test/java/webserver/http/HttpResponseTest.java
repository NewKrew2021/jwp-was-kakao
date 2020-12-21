package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.config.ServerConfigConstants;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    private final String testDirectory = "src/test/resources-test/";
    private BufferedReader bufferedReader;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_HTTP"));
        bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    @Test
    void response_html() throws Exception {
        HttpResponse httpResponse = new HttpResponse(createOutputStream("OUTPUT"));
        httpResponse.forward(FileIoUtils.loadFileFromClasspath(ServerConfigConstants.TEMPLATES_RESOURCE_PATH_PREFIX + "/index.html"));
        System.out.println(new String(httpResponse.getBody()));

    }

    @Test
    void response_file() throws Exception {
        HttpResponse httpResponse = new HttpResponse(createOutputStream("OUTPUT"));
        httpResponse.forward(FileIoUtils.loadFileFromClasspath(ServerConfigConstants.STATIC_RESOURCE_PATH_PREFIX + "/css/styles.css"));
        System.out.println(new String(httpResponse.getBody()));

    }

    @Test
    void response_redirect() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(bufferedReader);
        HttpResponse httpResponse = new HttpResponse(createOutputStream("OUTPUT"));
        httpResponse.sendRedirect(ResponseHeader.of(httpRequest), "/user/login.html");
        assertThat(httpResponse.getStatus()).isEqualTo(ResponseStatus.FOUND);

    }

    @Test
    void response_error() throws FileNotFoundException {
        HttpResponse httpResponse = new HttpResponse(createOutputStream("OUTPUT"));
        httpResponse.error();
        assertThat(httpResponse.getStatus()).isEqualTo(ResponseStatus.NOT_FOUND);

    }

    private OutputStream createOutputStream(String fileName) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + fileName));
    }

}
