package webserver;

import com.fasterxml.jackson.databind.util.JSONPObject;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import utils.FileIoUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
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

    @DisplayName("/index.html로 접근시 index.html을 잘읽어오는지 확인")
    @Test
    void request_user_create() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> postMap = new LinkedMultiValueMap<>();
        postMap.add("userId" , "abc");
        postMap.add("password" , "1234");
        postMap.add("name" , "def");
        postMap.add("email" , "abc@email.com");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(postMap,headers );

        String resourceUrl = "http://localhost:8080";
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl + "/user/create", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

}
