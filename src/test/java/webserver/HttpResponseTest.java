package webserver;

import model.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import utils.ResourceLoader;
import view.OutputView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HTTP 응답 관련 기능")
class HttpResponseTest {
    private static final String TEST_RESOURCE_PATH = "./src/test/resources/";

    @DisplayName("index.html forward 결과를 출력한다.")
    @Test
    void forward() throws Exception {
        // given
        OutputStream out = createOutputStream("Http_Forward_Actual.txt");
        OutputView outputView = OutputView.from(out);
        String path = "/index.html";

        // when
        Resource resource = ResourceLoader.getResource(path);
        HttpResponse response = HttpResponse.ok(resource);
        outputView.write(response);

        // then
        assertThat(FileIoUtils.hasSameContents(
                TEST_RESOURCE_PATH + "HTTP_Forward_Actual.txt",
                TEST_RESOURCE_PATH + "HTTP_Forward_Expected.txt"))
                .isTrue();
    }

    private OutputStream createOutputStream(String fileName) throws FileNotFoundException {
        return new FileOutputStream(TEST_RESOURCE_PATH + fileName);
    }

    @DisplayName("index.html redirect 결과를 출력한다.")
    @Test
    void redirect() throws Exception {
        // given
        OutputStream out = createOutputStream("Http_Redirect_Actual.txt");
        OutputView outputView = OutputView.from(out);
        String path = "/index.html";

        // when
        HttpResponse response = HttpResponse.redirect(path);
        outputView.write(response);

        // then
        assertThat(FileIoUtils.hasSameContents(
                TEST_RESOURCE_PATH + "HTTP_Redirect_Actual.txt",
                TEST_RESOURCE_PATH + "HTTP_Redirect_Expected.txt"))
                .isTrue();
    }

    @DisplayName("쿠키에 로그인 정보를 담는다.")
    @Test
    void cookie() throws Exception {
        // given
        OutputStream out = createOutputStream("Http_Cookie_Actual.txt");
        OutputView outputView = OutputView.from(out);
        String path = "/index.html";

        // when
        HttpResponse response = HttpResponse.redirect(path);
        response.addHeader("Set-Cookie", "logined=true; Path=/");
        outputView.write(response);

        // then
        assertThat(FileIoUtils.hasSameContents(
                TEST_RESOURCE_PATH + "HTTP_Cookie_Actual.txt",
                TEST_RESOURCE_PATH + "HTTP_Cookie_Expected.txt"))
                .isTrue();
    }
}
