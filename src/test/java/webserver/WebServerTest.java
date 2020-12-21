package webserver;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebServerTest {
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

    @Test
    @Order(1)
    void user_create() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("userId", "javajigi");
        parameters.add("password", "password");
        parameters.add("name", "박재성");
        parameters.add("email", "javajigi@slipp.net");

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8080/user/create",
                new HttpEntity<>(parameters, headers),
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/index.html");
    }

    @Test
    @Order(2)
    void user_login_success() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("userId", "javajigi");
        parameters.add("password", "password");

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8080/user/login",
                new HttpEntity<>(parameters, headers),
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/index.html");
    }

    @Test
    @Order(2)
    void user_login_failed() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("userId", "javajigi");
        parameters.add("password", "invalid_password");

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8080/user/login",
                new HttpEntity<>(parameters, headers),
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/user/login_failed.html");
    }

    @Test
    void user_list() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "logined=true");
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/user/list",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("<th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>");
    }

    @ParameterizedTest
    @CsvSource({
            "/css/styles.css, text/css",
            "/images/80-text.png, image/png",
            "/js/scripts.js, text/javascript"
    })
    void content_type(String path, String contentType) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:8080" + path,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.valueOf(contentType));
    }
}
