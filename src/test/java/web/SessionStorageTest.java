package web;

import org.junit.jupiter.api.Test;
import utils.IOUtils;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class SessionStorageTest {

    @Test
    void createNewIfSessionIdNotExist() {
        String data = "GET / HTTP/1.1" + IOUtils.CRLF +
                "Host: localhost:8080" + IOUtils.CRLF;

        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(data.getBytes()));
        HttpSession httpSession = SessionStorage.from(httpRequest);

        assertThat(httpSession).isNotNull();
        assertThat(httpSession.isValid()).isTrue();
    }

    @Test
    void createNewIfSessionInvalid() {
        String given = "GET / HTTP/1.1" + IOUtils.CRLF +
                "Host: localhost:8080" + IOUtils.CRLF;
        HttpSession expired = SessionStorage.from(HttpRequest.of(new ByteArrayInputStream(given.getBytes())));
        String expiredId = expired.getId();
        expired.invalidate();

        String data = "GET / HTTP/1.1" + IOUtils.CRLF +
                "Host: localhost:8080" + IOUtils.CRLF +
                "Cookie: session-id=" + expiredId + IOUtils.CRLF;
        HttpSession httpSession = SessionStorage.from(HttpRequest.of(new ByteArrayInputStream(data.getBytes())));

        assertThat(httpSession.getId()).isNotEqualTo(expiredId);
        assertThat(httpSession.isValid()).isTrue();
    }

    @Test
    void createNewIfSessionIsNotExist() {
        String notExistId = "not-exist";
        String data = "GET / HTTP/1.1" + IOUtils.CRLF +
                "Host: localhost:8080" + IOUtils.CRLF +
                "Cookie: session-id=" + notExistId + IOUtils.CRLF;
        HttpSession httpSession = SessionStorage.from(HttpRequest.of(new ByteArrayInputStream(data.getBytes())));

        assertThat(httpSession.getId()).isNotEqualTo(notExistId);
        assertThat(httpSession.isValid()).isTrue();
    }

    @Test
    void getExistSession() {
        String given = "GET / HTTP/1.1" + IOUtils.CRLF +
                "Host: localhost:8080" + IOUtils.CRLF;
        HttpSession alreadyExistSession = SessionStorage.from(HttpRequest.of(new ByteArrayInputStream(given.getBytes())));

        String data = "GET / HTTP/1.1" + IOUtils.CRLF +
                "Host: localhost:8080" + IOUtils.CRLF +
                "Cookie: session-id=" + alreadyExistSession.getId() + IOUtils.CRLF;
        HttpSession httpSession = SessionStorage.from(HttpRequest.of(new ByteArrayInputStream(data.getBytes())));

        assertThat(httpSession).isEqualTo(alreadyExistSession);
    }
}
