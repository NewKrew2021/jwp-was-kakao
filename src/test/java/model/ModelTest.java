package model;

import model.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ModelTest {
    @Test
    void isEmpty() {
        assertThat(Model.empty().isEmpty()).isEqualTo(true);
    }

    @Test
    void getData() {
        Model model = Model.empty();

        model.put("user", User.nobody());
        
        assertThat(model.getData()).containsKey("user");
        assertThat(model.getData().get("user")).isNotNull();
    }
}