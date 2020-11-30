package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {
    @Test
    void isRedirect() {
        Response response = new Response();
        response.setHeaders("Location: /index.html");
        assertThat(response.isRedirect()).isTrue();
    }
}
