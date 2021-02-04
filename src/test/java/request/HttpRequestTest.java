package request;

import annotation.web.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requestTextForTest.FilePathName;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {

    @DisplayName("POST 방식에서 요청이 들어 왔을 때, HttpRequest가 요청을 제대로 저장하고 있는지 확인한다.")
    @Test
    public void request_POST() throws Exception {
        //given
        InputStream in = new FileInputStream(FilePathName.POST_HTTP_CREATE_USER_REQUEST);

        //when
        HttpRequest request = HttpRequest.from(in);

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
        InputStream in = new FileInputStream(FilePathName.POST_HTTP_CREATE_USER_REQUEST);

        //when
        HttpRequest request = HttpRequest.from(in);

        //then
        assertEquals(RequestMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
        assertEquals("1", request.getParameter("id"));
    }

}