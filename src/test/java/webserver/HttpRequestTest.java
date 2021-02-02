package webserver;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    private static String port = "8080";

    @BeforeAll
    public static void setUp() {
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    WebServer.main(new String[]{port});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(thread).start();
    }

    @Test
    void request_resttemplate() {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void fileRequestTest(){
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/index.html";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
        try {
            byte[] actual = FileIoUtils.loadFileFromClasspath("templates/index.html");
            assertThat(response.getBody().getBytes(StandardCharsets.UTF_8)).isEqualTo(actual);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    void userCreateTest(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "userId=javagigi&password=p&name=a&email=email@email.com";
        HttpEntity<String> request =new HttpEntity<>(body, headers);

        String resourceUrl = "http://localhost:8080/user/create";
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }
}
