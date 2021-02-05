package model;

import org.junit.jupiter.api.Test;
import webserver.HttpRequest;

import java.io.*;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CookieTest {
    private String testDirectory = "./src/test/resources/controller";

    @Test
    void loginCookie() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_UserList.txt"));

        Cookie cookie = new Cookie(httpRequest);

        assertThat(cookie.isLogin()).isEqualTo(true);
    }


    private BufferedReader createBufferedReader(String filename) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + filename));
        return new BufferedReader(new InputStreamReader(in));
    }
}