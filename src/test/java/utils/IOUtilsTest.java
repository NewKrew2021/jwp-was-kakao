package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @DisplayName("현재 위치에서 남아 있는 모든데이터를 읽어온다")
    @Test
    void readAllRemainingData() throws IOException {
        String body = "name=nio&email=nio@kakaocorp.com";
        String data = String.join("\r\n", Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: Keep-Alive",
                "Content-Length: " + body.length(),
                "User-Agent: Apache-HttpClient/4.5.12 (Java/11.0.8)",
                "\r\n",
                body
        ));
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        br.readLine();
        br.readLine();
        br.readLine();
        br.readLine();
        br.readLine();
        br.readLine();

        String remainingData = IOUtils.readAllRemainingData(br);

        assertThat(remainingData).isEqualTo(body);
    }
}
