package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WebServerConfigTest {

    @DisplayName("port 가 0 보다 작게 설정되면 기본포트를 return 한다")
    @Test
    void getDefaultValue(){
        WebServerConfig config = WebServerConfig.configurer(it -> {
            it.setPort(-1);
            return it;
        });

        assertThat(config.getPortOrDefault(8080))
                .isEqualTo(8080);
    }

    @DisplayName("configurer 로 config 할 수 있다")
    @Test
    void useConfigurer(){
        WebServerConfig config = WebServerConfig.configurer(config1 -> {
            config1.setPort(8080);
            return config1;
        });
    }

}