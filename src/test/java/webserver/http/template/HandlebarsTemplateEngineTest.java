package webserver.http.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HandlebarsTemplateEngineTest {

    TemplateEngine templateEngine;

    @BeforeEach
    void setUp(){
        templateEngine = TemplateEngine.handlebars();
    }

    @Test
    void test(){
        String input = "hello {{name}}";
        Map<String, String> data = new HashMap<>();
        data.put("name", "nio");

        String output = templateEngine.apply(input, data);

        assertThat(output).isEqualTo("hello nio");
    }

}