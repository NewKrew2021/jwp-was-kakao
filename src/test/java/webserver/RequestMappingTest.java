package webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;

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
        RequestMapping requestMapping = new RequestMapping("/index", () -> {});

        assertThatThrownBy(() -> requestMapping.getController("/non_exists"))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Not Found: %s", "/non_exists");
    }

    private static class RequestMapping {
        private final Map<String, Controller> uriMapping;

        public RequestMapping(String uri, Controller indexController) {
            this(ImmutableMap.of(uri, indexController));
        }

        public RequestMapping(Map<String, Controller> uriMapping) {

            this.uriMapping = uriMapping;
        }

        public Controller getController(String uri) {
            Controller controller = uriMapping.get(uri);
            if (Objects.isNull(controller)) {
                throw new ControllerNotFoundException(uri);
            }
            return controller;
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

    private static class ControllerNotFoundException extends RuntimeException {
        public ControllerNotFoundException(String uri) {
            super(String.format("Not Found: %s", uri));
        }
    }
}
