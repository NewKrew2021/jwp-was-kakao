package webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {
    @Test
    void handleUserCreate() {
        HttpRequest httpRequest = new HttpRequest("GET", "/usr/create", "HTTP 1.1");
        httpRequest.setEntity(ImmutableMap.of("userId", "red"));
        assertThat(RequestHandler.handleUserCreate(httpRequest).getLocation())
                .isEqualTo("/index.html");
    }
}
