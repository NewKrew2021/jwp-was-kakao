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

    @Test
    void setStatusWith302() {
        Response response = new Response();
        response.setStatus("302 Found");
        assertThat(response.isRedirect()).isTrue();
        assertThat(response.getHeaders()).containsExactly("HTTP/1.1 302 Found ");
    }


    @Test
    void defaultStatus() {
        Response response = new Response();
        assertThat(response.isRedirect()).isFalse();
        assertThat(response.getHeaders()).containsExactly("HTTP/1.1 200 OK ");
    }
}
