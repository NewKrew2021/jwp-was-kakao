package webserver.http.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HandlebarsTemplateEngineTest {

    TemplateEngine templateEngine;

    @BeforeEach
    void setUp(){
        templateEngine = TemplateEngine.handlebars();
    }

    @DisplayName("기본으로 /templates 디렉토리 하위에서 .html 확장자를 가지는 파일을 로드한다")
    @Test
    void path(){
        String path = "handlebars";
        Map<String, String> data = new HashMap<>();
        data.put("name", "nio");

        String output = templateEngine.apply(path, data);
        assertThat(output).isEqualTo("hello nio");
    }

    @DisplayName("template 파일이 존재하지 않으면 exception 을 던진다")
    @Test
    void notExist(){
        String path = "notexist";
        Map<String, String> data = new HashMap<>();
        data.put("name", "nio");

        assertThatThrownBy( () -> templateEngine.apply(path, data) )
                .isInstanceOf(TemplateEngineException.class);
    }

}