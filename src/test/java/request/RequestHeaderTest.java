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

public class RequestHeaderTest {

    private static RequestHeader requestHeader;
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @DisplayName("")
    @BeforeAll
    public static void setup() throws IOException {
        String header = "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n";

        byte[] data = header.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        requestHeader = RequestHeader.of(br, logger);
    }

    @DisplayName("request Header의 key가 주어졌을 때 getHeaderValue를 통해 해당하는 Header의 value가 반환된다.")
    @ParameterizedTest
    @CsvSource({"Host,localhost:8080", "Connection,keep-alive", "Content-Length,59", "Content-Type,application/x-www-form-urlencoded"})
    public void header_값_테스트(String givenHeader, String expected) {
        //given

        //when, then
        assertThat(requestHeader.getHeaderValue(givenHeader)).isEqualTo(Optional.of(expected));
    }

}
