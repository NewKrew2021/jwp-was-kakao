package webserver.http;

import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.RequestMappingHandler;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    @Test
    void response_html() throws Exception {
        ResponseHeader responseHeader = new ResponseHeader();
        HttpResponse httpResponse = new HttpResponse(responseHeader,
                FileIoUtils.loadFileFromClasspath(RequestMappingHandler.TEMPLATES_RESOURCE_PATH_PREFIX + "/index.html"));
        System.out.println(new String(httpResponse.getBody()));

    }

    @Test
    void response_file() throws Exception {
        ResponseHeader responseHeader = new ResponseHeader();
        HttpResponse httpResponse = new HttpResponse(responseHeader,
                FileIoUtils.loadFileFromClasspath(RequestMappingHandler.STATIC_RESOURCE_PATH_PREFIX + "/css/styles.css"));
        System.out.println(new String(httpResponse.getBody()));

    }

    @Test
    void response_redirect() {
        HttpResponse httpResponse = new HttpResponse().redirect("/index.html");
        assertThat(httpResponse.getStatus()).isEqualTo(ResponseStatus.FOUND);

    }

    @Test
    void response_error() {
        HttpResponse httpResponse = new HttpResponse().error();
        assertThat(httpResponse.getStatus()).isEqualTo(ResponseStatus.NOT_FOUND);

    }

}
