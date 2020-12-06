package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WebServerTest {

    @DisplayName("서버포트가 80 이하로 설정하면 exception 을 던진다")
    @Test
    void invalidPort(){
        WebServer webServer = new WebServer(null);
        assertThatThrownBy(() -> webServer.start(80))
                .isInstanceOf(RuntimeException.class);
    }



}