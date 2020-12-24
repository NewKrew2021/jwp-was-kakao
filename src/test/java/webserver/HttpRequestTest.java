package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
    void request_index_html() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        String body = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body.contains("</html>")).isEqualTo(true);
    }

}
