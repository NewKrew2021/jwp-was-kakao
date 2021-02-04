package request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import requestTextForTest.FilePathName;
import webserver.RequestHandler;

import java.io.*;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestBodyTest {

    private static RequestHeader requestHeader;
    private static RequestBody requestBody;
    private static BufferedReader br;
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @BeforeAll
    public static void setup() throws IOException {
        //given
        InputStream in = new FileInputStream(FilePathName.POST_HTTP_CREATE_USER_REQUEST);

        br = new BufferedReader(new InputStreamReader(in));
        br.readLine();
        requestHeader = RequestHeader.of(br, logger);
        requestBody = RequestBody.of(br, requestHeader.getContentLength());
    }

    @DisplayName("request body 값이 요청으로 들어 왔을때 getBodyValue 로 이를 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource({"userId,javajigi", "password,password", "name,JaeSung"})
    public void 요청_바디_값_추출_테스트(String givenParam, String expected) {
        //given, when
        //then
        assertThat(requestBody.getBodyValue(givenParam)).isEqualTo(Optional.of(expected));
    }

}
