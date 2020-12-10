package webserver;

import dto.ParamValue;
import dto.RequestValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {

    private String testDirectory = "./src/test/resources/";

    @DisplayName("Request 생성 테스트")
    @Test
    void create() {
        String requestHeader = "GET /index.html HTTP/1.1";
        RequestValue requestValue = parseRequestValue(requestHeader);

        assertThat(Request.of(requestValue)).isEqualToComparingOnlyGivenFields(Request.of(requestValue));
    }

    @DisplayName("Get Param 파싱 테스트 : Param 존재 여부 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "GET /index.html HTTP/1.1 | false",
            "GET /user/create?userId=javajigi&password=password  HTTP/1.1 | true"
    }, delimiter = '|')
    void getGetParams1(String header, boolean existParam) {
        Request request = Request.of(parseRequestValue(header));

        assertThat(request.getParamMap().isPresent()).isEqualTo(existParam);
    }

    @DisplayName("Get Param 파싱 테스트 : 데이터 존재 시 정상적으로 파싱하는지 확인")
    @Test
    void getGetParams2() {
        String header = "GET /user/create?userId=javajigi&password=password  HTTP/1.1";
        Request request = Request.of(parseRequestValue(header));
        Optional<ParamValue> params = request.getParamMap();

        assertThat(params).isNotEmpty();
        assertThat(params.get().getValue("userId")).isEqualTo("javajigi");
        assertThat(params.get().getValue("password")).isEqualTo("password");
    }

    @DisplayName("Post Param 파싱 테스트")
    @Test
    void getPostParams() {
        List<String> requestHeader = Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                "",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        );
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        RequestValue requestValue = new RequestValue(requestHeader, body);
        Request request = Request.of(requestValue);

        Optional<ParamValue> params = request.getParamMap();

        assertThat(params).isNotEmpty();
        assertThat(params.get().getValue("userId")).isEqualTo("javajigi");
        assertThat(params.get().getValue("password")).isEqualTo("password");
    }

    @DisplayName("InputStream GET Request 생성 테스트")
    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        RequestValue requestValue = RequestValue.of(in);
        Request request = Request.of(requestValue);

        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPathGateway()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");

        Optional<ParamValue> paramMap = request.getParamMap();
        assertThat(paramMap).isNotEmpty();
        assertThat(paramMap.get().getValue("userId")).isEqualTo("javajigi");
        assertThat(paramMap.get().getValue("password")).isEqualTo("password");
        assertThat(paramMap.get().getValue("name")).isEqualTo("JaeSung");
    }

    @DisplayName("InputStream POST Request 생성 테스트")
    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        RequestValue requestValue = RequestValue.of(in);
        Request request = Request.of(requestValue);

        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPathGateway()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");

        Optional<ParamValue> paramMap = request.getParamMap();
        assertThat(paramMap).isNotEmpty();
        assertThat(paramMap.get().getValue("userId")).isEqualTo("javajigi");
        assertThat(paramMap.get().getValue("password")).isEqualTo("password");
        assertThat(paramMap.get().getValue("name")).isEqualTo("JaeSung");
    }

    private RequestValue parseRequestValue(String header) {
        List<String> requestHeader = Arrays.asList(header);
        return new RequestValue(requestHeader, "");
    }
}
