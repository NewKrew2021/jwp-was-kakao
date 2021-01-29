package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
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
        response.getHeaders().keySet().stream().forEach(it -> {
            System.out.println(it + ": " + response.getHeaders().get(it));});
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("GET /user/form.html")
    void request_resttemplate_요구사항2_1() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/user/form.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        response.getHeaders().keySet().stream().forEach(it -> {
            System.out.println(it + ": " + response.getHeaders().get(it));});
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("GET /user/create?pathvariable")
    void request_resttemplate_요구사항2_2() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        String url = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        response.getHeaders().keySet().stream().forEach(it -> {
            System.out.println(it + ": " + response.getHeaders().get(it));});
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("POST /user/create, body : variable")
    void request_resttemplate_요구사항3() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        String url = "/user/create";
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl + url,"userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);

        response.getHeaders().keySet().stream().forEach(it -> {
            System.out.println(it + ": " + response.getHeaders().get(it));});
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("로그인 - 실패")
    void request_resttemplate_요구사항5_로그인_실패() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        String urlCreate = "/user/create";
        String urlLogin = "/user/login";
        restTemplate.postForEntity(resourceUrl + urlCreate,
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",String.class);
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl + urlLogin,
                "userId=javajigi&password=password2",String.class);

        response.getHeaders().keySet().stream().forEach(it -> {
            System.out.println(it + ": " + response.getHeaders().get(it));});
        System.out.println(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
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
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",String.class);
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl + urlLogin,
                "userId=javajigi&password=password",String.class);

        response.getHeaders().keySet().stream().forEach(it -> {
            System.out.println(it + ": " + response.getHeaders().get(it));});
        System.out.println(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get("Location").get(0).equals("/index.html")).isTrue();
        assertThat(response.getHeaders().get("Set-Cookie").get(0).equals("logined=true; Path=/")).isTrue();
    }

}
