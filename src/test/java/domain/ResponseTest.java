package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Response 클래스")
public class ResponseTest {

    @Test
    public void defaultFileTest() throws IOException, URISyntaxException {
        String requestUrl = "/css/styles.css";
        ContentType type = ContentType.CSS;
        ResponseBody responseBody = new ResponseBody(FileIoUtils.loadFileFromClasspath("./static" + requestUrl));
        Response response = Response.ofDefaultFile(responseBody, type);
        assertThat(response.toString())
                .contains("HTTP/1.1 200 OK")
                .contains("Content-Type: text/css;charset=utf-8");
    }

    @Test
    public void redirectTest() {
        String requestUrl = "/index.html";
        Response response = Response.ofRedirect(requestUrl);
        assertThat(response.toString())
                .contains("HTTP/1.1 302 Found")
                .contains("Content-Type: text/html;charset=utf-8")
                .contains("Location: /index.html");
    }

    @Test
    public void dynamicHtmlTest(){
        String html = "<html><head><title>This is title</title></head><body></body></html>";
        Response response = Response.ofDynamicHtml(new ResponseBody(html));
        assertThat(response.toString())
                .contains("HTTP/1.1 200 OK")
                .contains("Content-Type: text/html;charset=utf-8")
                .contains("<html>").contains("</html>");
    }
}
