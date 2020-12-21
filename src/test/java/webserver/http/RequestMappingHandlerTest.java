package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestMappingHandler;
import webserver.util.StaticResourceUtils;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestMappingHandlerTest {

    private final String testDirectory = "src/test/resources-test/";

    @DisplayName("RequestMappingHandler static클래스 생성자 호출시 오류")
    @Test
    public void RequestMappingHandlerInitTest() throws IOException {
        assertThatThrownBy(() -> new RequestMappingHandler()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void userController_GET_USER_LOGIN() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_USER_LOGIN"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest request = new HttpRequest(bufferedReader);
        HttpResponse httpResponse = new HttpResponse(createOutputStream("OUTPUT"));
        RequestMappingHandler.resolve(request, httpResponse);
        InputStream output = new FileInputStream(new File(testDirectory + "OUTPUT"));
        BufferedReader outputBufferedReader = new BufferedReader(new InputStreamReader(output));
        assertThat(outputBufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK ");
    }

    @Test
    public void isStaticResourceTest() throws IOException {
        assertThat(StaticResourceUtils.isStaticResourcePath("/static/css/style.css")).isTrue();
        assertThat(StaticResourceUtils.isStaticResourcePath("/user/list")).isFalse();
    }

    private OutputStream createOutputStream(String fileName) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + fileName));
    }
}
