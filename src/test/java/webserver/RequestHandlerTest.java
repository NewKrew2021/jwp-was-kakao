package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RequestHandlerTest {
    @Test
    @DisplayName("GET edu.nextstep.camp")
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("GET /index.html")
    void request_resttemplate_요구사항1() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        printResponse(response);
    }

    @Test
    @DisplayName("GET /user/form.html")
    void request_resttemplate_요구사항2() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/user/form.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        printResponse(response);
    }

    @Test
    @DisplayName("POST /user/create, body : variable")
    void request_resttemplate_요구사항3() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        String url = "/user/create";
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl + url, "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);

        printResponse(response);
    }

    @Test
    @DisplayName("로그인 - 실패")
    void request_resttemplate_요구사항5_로그인_실패() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        String urlCreate = "/user/create";
        String urlLogin = "/user/login";
        restTemplate.postForEntity(resourceUrl + urlCreate,
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net", String.class);
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl + urlLogin,
                "userId=javajigi&password=password2", String.class);

        printResponse(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Location").get(0).equals("/user/login_failed.html")).isTrue();
        assertThat(response.getHeaders().get("Set-Cookie").get(0).equals("logined=false; Path=/")).isTrue();
    }

    @Test
    @DisplayName("로그인")
    void request_resttemplate_요구사항5() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        String urlCreate = "/user/create";
        String urlLogin = "/user/login";
        restTemplate.postForEntity(resourceUrl + urlCreate,
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net", String.class);
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl + urlLogin,
                "userId=javajigi&password=password", String.class);

        printResponse(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Location").get(0).equals("/index.html")).isTrue();
        assertThat(response.getHeaders().get("Set-Cookie").get(0).equals("logined=true; Path=/")).isTrue();
    }

    @Test
    @DisplayName("로그인 쿠키 존재함")
    void request_resttemplate_요구사항6_1() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        String url = "/user/create";
        restTemplate.postForEntity(resourceUrl + url, "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net", String.class);
        String urlList = "/user/list";
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "logined=true");
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(resourceUrl + urlList, HttpMethod.GET, entity, String.class);
        printResponse(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("로그인 쿠키 존재하지 않음")
    void request_resttemplate_요구사항6_2() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        String urlList = "/user/list";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + urlList, String.class);
        printResponse(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("CSS 파일 요청")
    void request_resttemplate_요구사항7() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        String urlList = "/css/styles.css";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + urlList, String.class);
        printResponse(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("405 잘못된 메서드")
    void request_resttemplate_405_bad_method() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        String url = "/user/create";

        assertThatExceptionOfType(HttpClientErrorException.MethodNotAllowed.class)
                .isThrownBy(() -> restTemplate.exchange(resourceUrl + url, HttpMethod.DELETE, null, String.class));
    }

    private void printResponse(ResponseEntity<String> response) {
        response.getHeaders().keySet().forEach(it -> {
            System.out.println(it + ": " + response.getHeaders().get(it));
        });
    }
}
