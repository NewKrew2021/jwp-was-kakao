package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.RequestMappingHandler;
import webserver.controller.MainController;
import webserver.controller.StaticResourceController;
import webserver.controller.UserController;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMappingHandlerTest {

    private final String testDirectory = "src/test/resources-test/";

    @Test
    public void getUserControllerTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_HTTP_CREATE_USER"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest request = new HttpRequest(bufferedReader);
        assertThat(RequestMappingHandler.getController(request)).isInstanceOf(UserController.class);
    }

    @Test
    public void getMainControllerTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_HTTP"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest request = new HttpRequest(bufferedReader);
        assertThat(RequestMappingHandler.getController(request)).isInstanceOf(MainController.class);
    }

    @Test
    public void getStaticResourceControllerTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_HTTP_CSS"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest request = new HttpRequest(bufferedReader);
        assertThat(RequestMappingHandler.getController(request)).isInstanceOf(StaticResourceController.class);
    }
}
