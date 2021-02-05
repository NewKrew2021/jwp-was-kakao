package webserver;

import controller.Controller;
import model.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceTest {
    @DisplayName("GET 메서드로 index.html을 요청하면 해당 리소스를 응답한다.")
    @Test
    void goHome() {
        // given
        Map<String, String> header = new HashMap<>();
        header.put("Host", "localhost:8080");
        header.put("Connection", "keep-alive");
        header.put("Accept", "*/*");
        String path = "/index.html";
        HttpRequest request = HttpRequest.of(HttpMethod.GET, path, header, new HashMap<>());
        Resource resource = ResourceLoader.getResource("/index.html");
        HttpResponse expected = HttpResponse.ok(resource);

        // when
        ControllerMapper mapper = ControllerMapper.getInstance();
        Controller controller = mapper.getController(path);
        HttpResponse actual = controller.service(request);

        // then
        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
        assertThat(actual.getBody()).isEqualTo(expected.getBody());
    }
}
