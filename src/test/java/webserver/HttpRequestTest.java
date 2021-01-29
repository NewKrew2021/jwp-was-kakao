package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
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
    void httpRequestDtoTest(){
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/user/create?userId=javagigi&password=p&name=a&email=email@email.com";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
        assertThat(response.getBody()).isEqualTo("성공!");
    }
}
