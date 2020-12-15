package utils.io;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    String data = "abcd123";
    StringReader sr = new StringReader(data);
    BufferedReader br = new BufferedReader(sr);

    @Test
    public void readData() {
        String readData = IOUtils.readData(br, data.length());
        logger.debug("parse body : {}", readData);
        assertThat(readData).isEqualTo(data);
    }

    @Test
    void failDuringReadData() {
        assertThatThrownBy(() -> {
            IOUtils.readData(br, -1);
        }).isInstanceOf(FileReadException.class);
    }
}
