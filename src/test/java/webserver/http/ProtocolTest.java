package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProtocolTest {

    @Test
    @DisplayName("프로토콜 조회")
    void getProtocol() {
        Protocol protocol = new Protocol("HTTP", "1.1");
        assertThat(protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(protocol.getVersion()).isEqualTo("1.1");
    }

    @Test
    @DisplayName("생성 오류")
    void 생성오류_확인() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            Protocol protocol = new Protocol("HTTP");
        });
    }

}