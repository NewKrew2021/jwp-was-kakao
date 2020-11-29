package webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestMappingTest {
    @DisplayName("매핑된 컨트롤러르 리턴한다")
    @Test
    void create() {
        IndexController indexController = new IndexController();
        RequestMapping requestMapping = new RequestMapping("/index", indexController);
        assertThat(requestMapping.getController("/index")).isEqualTo(indexController);
    }

    @DisplayName("여러개의 컨트롤러로 매핑이 가능하다")
    @Test
    void controllerMapping() {
        Map<String, Controller> uriMapping = ImmutableMap.of(
                "/index", new IndexController(),
                "/health_check", new HealthCheckController());

        RequestMapping requestMapping = new RequestMapping(uriMapping);

        uriMapping.forEach((uri, controller) ->
                                   assertThat(requestMapping.getController(uri)).isEqualTo(controller));
    }

    @DisplayName("매핑이 존재하지 않으면 예외가 발생한다")
    @Test
    void notFound() {
        RequestMapping requestMapping = new RequestMapping("/index", (httpRequest) -> null);

        assertThatThrownBy(() -> requestMapping.getController("/non_exists"))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Not Found: %s", "/non_exists");
    }

    private static class IndexController implements Controller {
        @Override
        public Response execute(HttpRequest httpRequest) {
            return null;
        }
    }

    private static class HealthCheckController implements Controller {
        @Override
        public Response execute(HttpRequest httpRequest) {
            return null;
        }
    }

}
