package domain;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(TEST_DIRECTORY + "create_user_sample.txt");
        HttpRequest request = HttpRequest.from(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getUrl());
        assertEquals("keep-alive", request.getHeader("Connection").get(0));
        assertEquals("javajigi", request.getParameter("userId"));
    }
}