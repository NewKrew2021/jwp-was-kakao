package request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestBodyTest {

    private static RequestHeader requestHeader;
    private static RequestBody requestBody;
    private static BufferedReader br;
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @BeforeAll
    public static void setup() throws IOException {
        String header = "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 42\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=박재성";

        byte[] data = header.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        br = new BufferedReader(inputStreamReader);
        requestHeader = RequestHeader.of(br, logger);
        requestBody = RequestBody.of(br, requestHeader.getContentLength());
    }

    @DisplayName("request body 값이 요청으로 들어 왔을때 getBodyValue 로 이를 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource({"userId,javajigi" , "password,password" , "name,박재성"})
    public void 요청_바디_값_추출_테스트(String givenParam, String expected){
        //given, when
        //then
        assertThat(requestBody.getBodyValue(givenParam)).isEqualTo(Optional.of(expected));
    }

}
