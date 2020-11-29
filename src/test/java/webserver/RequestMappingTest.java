package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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
    @ParameterizedTest
    @MethodSource("mappingConfig")
    void controllerMapping(String uri, Controller controller) {
        RequestMapping requestMapping = new RequestMapping(uri, controller);
        assertThat(requestMapping.getController("uri")).isEqualTo(controller);
    }

    private static Stream<Arguments> mappingConfig() {
        return Stream.of(
                Arguments.of("/index", new IndexController()),
                Arguments.of("/health_check", new HealthCheckController())
        );
    }

    private static class IndexController {

    }

    private static class RequestMapping {
        private final String uri;
        private final IndexController indexController;

        public RequestMapping(String uri, IndexController indexController) {
            this.uri = uri;
            this.indexController = indexController;
        }

        public IndexController getController(String uri) {
            if (uri.equals(uri)) {
                return indexController;
            }
            return null;
        }
    }
}
