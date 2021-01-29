package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

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

    @Test
    void readRequest() {
        String data = "GET / HTTP/1.1" + IOUtils.NEW_LINE +
                "Host: localhost:8080" + IOUtils.NEW_LINE +
                "Connection: keep-alive" + IOUtils.NEW_LINE +
                "Upgrade-Insecure-Requests: 1" + IOUtils.NEW_LINE +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36" + IOUtils.NEW_LINE +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" + IOUtils.NEW_LINE +
                "Sec-Fetch-Site: none" + IOUtils.NEW_LINE +
                "Sec-Fetch-Mode: navigate" + IOUtils.NEW_LINE +
                "Sec-Fetch-User: ?1" + IOUtils.NEW_LINE +
                "Sec-Fetch-Dest: document" + IOUtils.NEW_LINE +
                "Accept-Encoding: gzip, deflate, br" + IOUtils.NEW_LINE +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7" + IOUtils.NEW_LINE;

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes())));
        assertThat(IOUtils.readRequestUntilHeader(br).get(0)).isEqualTo("GET / HTTP/1.1");
    }

}
