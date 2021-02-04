package webserver;

import annotation.web.RequestMethod;
import model.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("GET 방식 데이터 전달시 request에 path, header, parameter로 저장되어야 한다.")
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        Request request = Request.of(in);

        assertEquals(RequestMethod.valueOf("GET"), request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    @DisplayName("GET 방식 파라미터가 없는경우 Request 생성 테스트")
    public void request_GET_no_parameter() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET2.txt"));
        Request request = Request.of(in);

        assertEquals(RequestMethod.valueOf("GET"), request.getMethod());
        assertEquals("/user/list", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
    }


    @Test
    @DisplayName("POST 방식 데이터 전달시 request에 path, header, parameter로 저장되어야 한다.")
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        Request request = Request.of(in);

        assertEquals(RequestMethod.valueOf("POST"), request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    @DisplayName("POST 방식으로 데이터를 전달할 때 body를 통한 데이터 전달뿐만 아니라 Query String을 활용한 데이터 전달도 지원해야 한다.")
    public void request_POST_with_querystring() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
        Request request = Request.of(in);

        assertEquals(RequestMethod.valueOf("POST"), request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("1", request.getParameter("id"));
        assertEquals("javajigi", request.getParameter("userId"));
    }
}
