package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
    void fileRequestTest() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:" + port + "/index.html";
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
    void userCreateTest() {
        RestTemplate restTemplate = new RestTemplate();
        String body = "userId=javajigi&password=p&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        ResponseEntity<String> response = 회원가입_요청(restTemplate, body);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    public ResponseEntity<String> 회원가입_요청(RestTemplate restTemplate, String body) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        String resourceUrl = "http://localhost:" + port + "/user/create";
        return restTemplate.postForEntity(resourceUrl, request, String.class);
    }

    @Test
    void loginFailTest() {
        RestTemplate restTemplate = new RestTemplate();
        String body = "userId=javajigi&password=p";
        ResponseEntity<String> response = 로그인_요청(restTemplate, body);
        assertThat(response.getHeaders().getLocation().toString()).isEqualTo("/user/login_failed.html");
    }

    @Test
    void loginSuccessTest() {
        RestTemplate restTemplate = new RestTemplate();
        String createBody = "userId=javajigi&password=p&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        회원가입_요청(restTemplate, createBody);

        String body = "userId=javajigi&password=p";
        ResponseEntity<String> response = 로그인_요청(restTemplate, body);
        assertThat(response.getHeaders().getLocation().toString()).isEqualTo("/index.html");
    }

    public ResponseEntity<String> 로그인_요청(RestTemplate restTemplate, String body) {
        String resourceUrl = "http://localhost:" + port + "/user/login";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(resourceUrl, request, String.class);
    }

    @Test
    void userListLoginSuccessTest() {
        RestTemplate restTemplate = new RestTemplate();
        String createBody = "userId=okok1&password=p&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        회원가입_요청(restTemplate, createBody);
        String createBody2 = "userId=nono2&password=p&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        회원가입_요청(restTemplate, createBody2);

        String body = "userId=nono2&password=p";
        ResponseEntity<String> login_response = 로그인_요청(restTemplate, body);

        String sessionId = login_response.getHeaders().getFirst(HttpHeaders.SET_COOKIE).split(";")[1].split("=")[1];
        String resourceUrl = "http://localhost:" + port + "/user/list";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "logined=true; " + sessionId);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void userListLoginFailTest() {
        RestTemplate restTemplate = new RestTemplate();
        String createBody = "userId=okok1&password=p&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        회원가입_요청(restTemplate, createBody);

        String resourceUrl = "http://localhost:" + port + "/user/list";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(resourceUrl, HttpMethod.GET, request, String.class);

        try {
            assertThat(response.getBody().getBytes(StandardCharsets.UTF_8))
                    .isEqualTo(FileIoUtils.loadFileFromClasspath("templates/user/login.html"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
