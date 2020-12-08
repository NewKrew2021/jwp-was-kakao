package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHeaderTest {

    private final String testDirectory = "src/test/resources-test/";
    HttpRequest request;

    @BeforeEach
    public void setUp() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_HTTP_CREATE_USER"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        request = new HttpRequest(bufferedReader);
    }

    @Test
    public void loginCookieTest(){
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setLoginCookie(request, true);
        assertThat(responseHeader.getHeader().get("Set-Cookie")).isEqualTo("logined=true;Path=/");
        responseHeader.setLoginCookie(request, false);
        assertThat(responseHeader.getHeader().get("Set-Cookie")).isEqualTo("logined=false;Path=/");
    }


}
