package request;

import annotation.web.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {


    @DisplayName("POST 방식에서 요청이 들어 왔을 때, HttpRequest가 요청을 제대로 저장하고 있는지 확인한다.")
    @Test
    public void request_POST() throws Exception {
        //given
        String requestString = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 46\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=JaeSung";

        byte[] data = requestString.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);

        //when
        HttpRequest request = HttpRequest.from(inputStream);

        //then
        assertEquals(RequestMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @DisplayName("POST 방식에서 요청의 첫 라인에 QueryString을 활용한 데이터 전달이 있더라도, 값을 제대로 저장하는지 확인한다.")
    @Test
    public void request_POST_2() throws Exception {
        //given
        String requestString = "POST /user/create?id=1 HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 46\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=JaeSung";

        byte[] data = requestString.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);

        //when
        HttpRequest request = HttpRequest.from(inputStream);

        //then
        assertEquals(RequestMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
        assertEquals("1", request.getParameter("id"));
    }

}