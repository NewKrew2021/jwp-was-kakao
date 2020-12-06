package webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.Response;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static webserver.http.HttpMethod.GET;
import static webserver.http.HttpMethod.POST;

public class RequestMappingTest {
    @DisplayName("매핑된 컨트롤러르 리턴한다")
    @Test
    void getController() {
        IndexController indexController = new IndexController();
        RequestMapping requestMapping = new RequestMapping(GET, "/index", indexController);
        assertThat(requestMapping.getController(GET, "/index")).isEqualTo(indexController);
    }

    @DisplayName("메소드로 매핑을 구분할 수 있다")
    @Test
    void shouldReturnNullWhenDifferentMethod() {
        IndexController indexController = new IndexController();
        RequestMapping requestMapping = new RequestMapping(GET, "/index", indexController);
        assertThat(requestMapping.getController(POST, "/index")).isNull();
    }

    @DisplayName("매핑 URI 는 메소드를 포힘해야 한다")
    @Test
    void mappingURI() {
        assertThatThrownBy(() -> new RequestMapping(ImmutableMap.of(
                "/index", new IndexController()))).isInstanceOf(InvalidMappingURIFormatException.class);
    }

    @DisplayName("여러개의 컨트롤러로 매핑이 가능하다")
    @Test
    void controllerMapping() {
        Map<String, Controller> uriMapping = ImmutableMap.of(
                "GET /index", new IndexController(),
                "GET /health_check", new HealthCheckController());

        RequestMapping requestMapping = new RequestMapping(uriMapping);

        uriMapping.forEach((uri, controller) ->
                                   assertThat(requestMapping.getController(uri)).isEqualTo(controller));
    }

    @Test
    void toMappingURI() {
        assertThat(RequestMapping.toMappingURI(GET, "/index")).isEqualTo("GET /index");
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
