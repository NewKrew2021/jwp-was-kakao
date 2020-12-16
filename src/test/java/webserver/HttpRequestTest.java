package webserver;

import app.Application;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.FileUtils;
import webserver.constant.HttpHeader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

// FIXME rename to app test
public class HttpRequestTest {

    private static final int DEFAULT_PORT = 8888;
    private static final String DEFAULT_HOST_URL = "http://localhost:" + DEFAULT_PORT;

    private static ExecutorService executorService;
    private static CloseableHttpClient httpClient;

    @BeforeAll
    public static void init() throws InterruptedException {
        startApplication();

        httpClient = HttpClients.createDefault();
    }

    private static void startApplication() throws InterruptedException {
        Application application = new Application();

        CountDownLatch startSignal = application.getStartSignal();

        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            application.run(DEFAULT_PORT);
            return null;
        });

        startSignal.await();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        httpClient.close();

        executorService.shutdown();
    }

    @Test
    public void helloWebServer() throws IOException {
        HttpGet httpGet = new HttpGet(DEFAULT_HOST_URL + "/hello");
        CloseableHttpResponse resp = httpClient.execute(httpGet);

        assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(resp.getFirstHeader("x-hello").getValue()).isEqualTo("World!");

        assertThat(EntityUtils.toString(resp.getEntity())).isEqualTo("Hello World!");
    }

    @Test
    public void helloStaticFile() throws IOException, URISyntaxException {
        String file = "/css/styles.css";

        HttpGet httpGet = new HttpGet(DEFAULT_HOST_URL + file);
        CloseableHttpResponse resp = httpClient.execute(httpGet);

        assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(resp.getFirstHeader(HttpHeader.CONTENT_TYPE).getValue()).isEqualTo("text/css; charset=utf-8");

        String indexFilePath = "./static" + file;

        String actual = EntityUtils.toString(resp.getEntity());
        String expected = new String(FileUtils.loadFileFromClasspath(indexFilePath));

        assertThat(actual).isEqualTo(expected);
    }

}
