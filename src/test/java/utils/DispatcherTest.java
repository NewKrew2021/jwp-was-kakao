package utils;

import controller.Handler;
import controller.StaticController;
import controller.TemplateController;
import controller.UserController;
import http.HttpRequest;
import http.HttpRequestParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherTest {
    public static final String PATH = "./src/test/resources/";

    @DisplayName("templateHandler를 반환하는지 테스트")
    @ParameterizedTest
    @CsvSource({"index-html.txt", "favicon-ico.txt"})
    void templateRequest(String path) throws IOException {
        HttpRequest request = new HttpRequestParser(IOUtils.readRequest(new FileInputStream(PATH + path))).parse();

        Handler handler = Dispatcher.findMatchingHandlers(request);

        assertThat(handler).isEqualTo(TemplateController.templateHandler);
    }

    @DisplayName("staticHandler를 반환하는지 테스트")
    @ParameterizedTest
    @CsvSource({"css.txt", "fonts.txt", "images.txt", "js.txt"})
    void staticRequest(String path) throws IOException {
        HttpRequest request = new HttpRequestParser(IOUtils.readRequest(new FileInputStream(PATH + path))).parse();

        Handler handler = Dispatcher.findMatchingHandlers(request);

        assertThat(handler).isEqualTo(StaticController.staticHandler);
    }

    @DisplayName("createUserHandler를 반환하는지 테스트")
    @Test
    void userCreateRequest() throws IOException {
        HttpRequest request = new HttpRequestParser(IOUtils.readRequest(new FileInputStream(PATH + "post-user-create.txt"))).parse();

        Handler handler = Dispatcher.findMatchingHandlers(request);

        assertThat(handler).isEqualTo(UserController.createUserHandler);
    }
}