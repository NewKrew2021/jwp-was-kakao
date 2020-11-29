package webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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

    private static class RequestMapping {
        private final Map<String, Controller> uriMapping;

        public RequestMapping(String uri, IndexController indexController) {
            this(ImmutableMap.of(uri, indexController));
        }

        public RequestMapping(Map<String, Controller> uriMapping) {

            this.uriMapping = uriMapping;
        }

        public Controller getController(String uri) {
            return uriMapping.get(uri);
        }

    }

    @FunctionalInterface
    private interface Controller {
        void execute();

    }

    private static class IndexController implements Controller {
        @Override
        public void execute() {

        }
    }

    private static class HealthCheckController implements Controller {
        @Override
        public void execute() {

        }
    }
}
