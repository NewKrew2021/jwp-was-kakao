package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

public class ParameterValidatorTest {
    @Test
    public void validate() throws IOException {
        String sample = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n";

        HttpRequest httpRequest = HttpRequest.Parser.parse(new ByteArrayInputStream(sample.getBytes()));

        assertThatCode(() ->
                ParameterValidator.validate(httpRequest, "userId", "password", "name", "email"))
                .doesNotThrowAnyException();

        assertThatIllegalArgumentException().isThrownBy(() ->
                ParameterValidator.validate(httpRequest, "error", "error2", "password"))
                .withMessageContaining("error")
                .withMessageContaining("error2");
    }
}
