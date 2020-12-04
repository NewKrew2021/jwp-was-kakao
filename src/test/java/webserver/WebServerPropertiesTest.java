package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WebServerPropertiesTest {

    @DisplayName("port 가 0 보다 작게 설정되면 기본포트를 return 한다")
    @Test
    void getDefaultValue(){
        WebServerProperties properties = WebServerProperties.builder()
                .port(-1)
                .build();

        assertThat(properties.getPortOrDefault(8080))
                .isEqualTo(8080);
    }

}