package webserver;

import dto.RequestValue;
import model.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class URIFactoryTest {

    private URIFactory uriFactory;

    @BeforeEach
    public void setup() {
        uriFactory = new URIFactory();
    }

    @Test
    void compose1() {
        String header = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
        Request request = build(header, "");

        Response response = uriFactory.create(request);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.HTTP_FOUND);
    }

    @Test
    void compose2() {
        String header = "POST /user/login HTTP/1.1";
        String body = "userId=javajigi&password=password";
        Request request = build(header, body);

        Response response = uriFactory.create(request);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.HTTP_FOUND);
    }

    private Request build(String header, String body){
        List<String> headerList = Arrays.asList(header);
        RequestValue requestValue = new RequestValue(headerList, body);
        return Request.of(requestValue);
    }
}
