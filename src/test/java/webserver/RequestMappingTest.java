package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMappingTest {
    @DisplayName("매핑된 컨트롤러르 리턴한다")
    @Test
    void create() {
        IndexController indexController = new IndexController();
        RequestMapping requestMapping = new RequestMapping("/index", indexController);
        assertThat(requestMapping.getController("/index")).isEqualTo(indexController);
    }

    private static class IndexController {

    }

    private class RequestMapping {
        private final String uri;
        private final IndexController indexController;

        public RequestMapping(String uri, IndexController indexController) {

            this.uri = uri;
            this.indexController = indexController;
        }

        public IndexController getController(String uri) {
            return indexController;
        }
    }
}
