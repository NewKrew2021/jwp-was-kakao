package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WebServerTest {

    @DisplayName("서버포트가 80 이하로 설정하면 exception 을 던진다")
    @Test
    void invalidPort() {
        WebServer webServer = new WebServer(WebServerConfig.configurer(it -> it));
        assertThatThrownBy(() -> webServer.start(80))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("config 로 null 을 입력하면 exception 을 던진다")
    @Test
    void nullConfig() {
        assertThatThrownBy(() -> new WebServer(null))
                .isInstanceOf(IllegalArgumentException.class);
    }


}