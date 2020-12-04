package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.http.parser.CookieParser;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieParserTest {
    @Test
    public void parse() {
        String sample = "name=value; name2=value2;name3=value3;  name4=value4";
        Map<String, String> cookie = CookieParser.parse(sample);
        assertThat(cookie)
                .containsEntry("name", "value")
                .containsEntry("name2", "value2")
                .containsEntry("name3", "value3")
                .containsEntry("name4", "value4");
    }
}
