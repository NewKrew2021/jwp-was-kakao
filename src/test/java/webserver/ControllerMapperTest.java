package webserver;

import controller.Controller;
import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMapperTest {
    private final ControllerMapper mapper = ControllerMapper.getInstance();
    private final Map<String, Controller> answer = getAnswer();

    private Map<String, Controller> getAnswer() {
        Map<String, Controller> map = new HashMap<>();
        map.put("/user/create", UserCreateController.getInstance());
        map.put("/user/login", UserLoginController.getInstance());
        map.put("/user/list", UserListController.getInstance());
        return Collections.unmodifiableMap(map);
    }

    @DisplayName("경로가 주어지면 해당 경로를 처리하는 컨트롤러를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"/user/create", "/user/login", "/user/list"})
    void getController(String path) {
        // given
        Controller expected = answer.get(path);

        // when
        Controller actual = mapper.getController(path);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
