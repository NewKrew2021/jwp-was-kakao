package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.http.parser.FormUrlencodedBodyParser;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class FormUrlencodedBodyParserTest {

    @Test
    public void parse() {
        String sample = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        Map<String, String> body = FormUrlencodedBodyParser.parse(sample);
        assertThat(body)
                .containsEntry("userId", "javajigi")
                .containsEntry("password", "password")
                .containsEntry("name", "박재성")
                .containsEntry("email", "javajigi@slipp.net");
    }
}
