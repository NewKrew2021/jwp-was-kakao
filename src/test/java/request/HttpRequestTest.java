package request;

import annotation.web.RequestMethod;
import controller.Controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import response.HttpResponse;
import webserver.ControllerHandler;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {

    @DisplayName("POST 방식에서 요청이 들어 왔을 때, HttpRequest가 요청을 제대로 저장하고 있는지 확인한다.")
    @Test
    public void request_POST() throws Exception {
        //given
        InputStream in = new FileInputStream("src/test/resources/requestTextForTest/postHttpCreateUserRequest");

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
        InputStream in = new FileInputStream("src/test/resources/requestTextForTest/postHttpCreateUserRequest");

        //when
        HttpRequest request = HttpRequest.from(in);

        //then
        assertEquals(RequestMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
        assertEquals("1", request.getParameter("id"));
    }

    @DisplayName("회원가입 후에 login 진행후 응답값으로 쿠키 헤더 가 true로 포함되어 있")
    @Test
    public void 로그인후_응답_쿠키값_테스트() throws IOException{
        //회원가입
        HttpResponse response1 = new HttpResponse( new ByteArrayOutputStream());

        HttpRequest request1 = HttpRequest.from(new FileInputStream("src/test/resources/requestTextForTest/postHttpCreateUserRequest"));

        ControllerHandler controllers = new ControllerHandler();
        Controller controller1 = controllers.getController(request1.getPath());
        controller1.service(request1, response1);


        //로그인
        File resultFile = new File("src/test/resources/responseTextForTest/responseResult");
        OutputStream outputStream = new FileOutputStream(resultFile);
        HttpResponse response2 = new HttpResponse(outputStream);

        FileInputStream fileInputStream = new FileInputStream("src/test/resources/requestTextForTest/postHttpLoginRequest");
        HttpRequest request2 = HttpRequest.from(fileInputStream);

        Controller controller = controllers.getController(request2.getPath());
        controller.service(request2, response2);

        InputStreamReader resultInputStreamReader = new FileReader(resultFile);
        BufferedReader br = new BufferedReader(resultInputStreamReader);
        StringBuilder resultResponse = null;
        String tmp;
        while(!(tmp = br.readLine()).equals("")){
            resultResponse = (resultResponse == null ? new StringBuilder("null") : resultResponse).append(tmp);
        }
        assertThat(resultResponse).contains("Set-Cookie: session=");
    }

    @DisplayName("리소스 파일을 요청시에 정상응답 코드를 받는다")
    @Test
    public void 리소스_파일_정상응답_테스트() throws IOException {
        File resultFile = new File("src/test/resources/responseTextForTest/responseResult");
        OutputStream outputStream = new FileOutputStream(resultFile);
        HttpResponse response = new HttpResponse(outputStream);

        FileInputStream fileInputStream = new FileInputStream("src/test/resources/requestTextForTest/getHttpStaticFileRequest");
        HttpRequest request = HttpRequest.from(fileInputStream);

        ControllerHandler controllers = new ControllerHandler();
        Controller controller = controllers.getController(request.getPath());
        controller.service(request, response);

        InputStreamReader resultInputStreamReader = new FileReader(resultFile);
        BufferedReader br = new BufferedReader(resultInputStreamReader);
        StringBuilder resultResponse = null;
        String tmp;
        while(!(tmp = br.readLine()).equals("")){
            resultResponse = (resultResponse == null ? new StringBuilder("null") : resultResponse).append(tmp);
        }
        assertThat(resultResponse).contains("HTTP/1.1 200 OK");
    }

}