package view;

import model.Model;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ViewResolverTest {
    ViewResolver resolver = new ViewResolver();

    @Test
    void notExistView() {
        assertThat(resolver.resolve(Model.empty(), "test.png")).isNull();
    }

    @Test
    void exist() {
        View view = resolver.resolve(Model.empty(), "./templates/index.html");

        assertThat(view).isNotNull();
        assertThat(view.getContentType()).isEqualTo("text/html");
    }
}