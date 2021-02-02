//package webserver;
//
//import model.RequestMessage;
//import model.Response;
//import model.ResponseOK;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DisplayName("index.html 관련 기능")
//public class ResourceTest {
//    @DisplayName("GET 메서드로 index.html을 요청하면 해당 리소스를 응답한다.")
//    @Test
//    void func() throws IOException, URISyntaxException {
//        //given
//        String requestLine = "GET /index.html HTTP/1.1";
//        Map<String, String> requestHeader = new HashMap<>();
//        requestHeader.put("Host", "localhost:8080");
//        requestHeader.put("Connection", "keep-alive");
//        requestHeader.put("Accept", "*/*");
//        String requestBody = "";
//
//        //when
//        RequestMessage requestMessage = RequestMessage.of(requestLine, requestHeader, requestBody);
//        Response response = ResourceController.handle(requestMessage);
//
//        //then
//        assertThat(response).isInstanceOf(ResponseOK.class);
//    }
//}
