package webserver;

import http.HttpHeader;
import model.PageUrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHandlerTest {

    private RestTemplate restTemplate;
    private HttpEntity<MultiValueMap<String, String>> request;
    private String resourceUrl = "http://localhost:8080";
    private HttpHeaders headers;


    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> postMap = new LinkedMultiValueMap<>();

        postMap.add("userId" , "abc");
        postMap.add("password" , "1234");
        postMap.add("name" , "def");
        postMap.add("email" , "abc@email.com");
        request = new HttpEntity<>(postMap, headers);
    }

    @DisplayName("/index.html로 접근시 index.html을 잘읽어오는지 확인")
    @Test
    void request_index() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/index.html", String.class);

        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource("./templates/index.html").toURI());
        byte[] bytes = Files.readAllBytes(path);

        assertThat(response.getBody()).isEqualTo(new String(bytes));
    }

    @DisplayName("회원가입")
    @Test
    void request_user_create() throws IOException, URISyntaxException {

        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl + "/user/create", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @DisplayName("로그인 성공")
    @Test
    void request_user_login() throws IOException, URISyntaxException {
        restTemplate.postForEntity(resourceUrl + "/user/create", request, String.class);

        MultiValueMap<String, String> postMap = new LinkedMultiValueMap<>();
        postMap.add("userId" , "abc");
        postMap.add("password" , "1234");
        HttpEntity<MultiValueMap<String, String>> compareRequest = new HttpEntity<>(postMap, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl + "/user/login", compareRequest, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:8080/index.html");
    }

    @DisplayName("로그인 실패")
    @Test
    void request_user_login_fail() throws IOException, URISyntaxException {
        restTemplate.postForEntity(resourceUrl + "/user/create", request, String.class);
        MultiValueMap<String, String> postMap = new LinkedMultiValueMap<>();
        postMap.add("userId" , "abc");
        postMap.add("password" , "5678");
        HttpEntity<MultiValueMap<String, String>> compareRequest = new HttpEntity<>(postMap, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl + "/user/login", compareRequest, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:8080/user/login_failed.html");
    }
}
