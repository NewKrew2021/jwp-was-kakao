package webserver.requestmapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("핸들러 매핑 테스트")
public class RequestMappingTest {
    @Test
    public void getAddUser() {
        assertThat(RequestMapping.findMethod("/user/create", HttpMethod.GET).getName()).isEqualTo("addUser");
    }
}