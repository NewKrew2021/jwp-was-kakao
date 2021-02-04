package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class URITest {
    @Test
    @DisplayName("생성 테스트")
    void createTest() {
        String url = "/user/create?userId=javajigi&password=password&name=JaeSung";
        URI uri = new URI(url);

        assertThat(uri.getPath()).isEqualTo("/user/create");
    }
}