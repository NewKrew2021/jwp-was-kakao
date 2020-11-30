package webserver;

import dto.ParamValue;
import model.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {

    @DisplayName("Response 생성 테스트")
    @Test
    void create() {
        Response response = Response.of(HttpStatus.HTTP_OK);

        assertThat(response).isEqualToComparingFieldByField(Response.of(HttpStatus.HTTP_OK));
    }

    @DisplayName("Response 생성 테스트")
    @Test
    void getAddHttpDesc() {
        Response response = Response.of(HttpStatus.HTTP_FOUND, ParamValue.of("Location", "/index.html"));

        assertThat(response.getAddHttpDesc().size()).isEqualTo(1);
        assertThat(response.getAddHttpDesc().get(0)).contains("Location: /index.html");
    }
}
