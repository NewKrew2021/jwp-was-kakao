package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParseUtilsTest {

    @Test
    void parseHeaderKey() {
        String header = "Host: localhost:8080";
        String expected = "Host";
        assertThat(ParseUtils.parseHeaderKey(header)).isEqualTo(expected);
    }

    @Test
    void parseHeaderValue() {
        String header = "Host: localhost:8080";
        String expected = "localhost:8080";
        assertThat(ParseUtils.parseHeaderValue(header)).isEqualTo(expected);
    }

}