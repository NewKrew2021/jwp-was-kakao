package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class FormUrlencodedBodyParserTest {

    @Test
    public void parse() throws UnsupportedEncodingException {
        String sample = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        assertThat(FormUrlencodedBodyParser.parse(sample))
                .containsEntry("userId", "javajigi")
                .containsEntry("password", "password")
                .containsEntry("name", "박재성")
                .containsEntry("email", "javajigi@slipp.net");
    }
}
