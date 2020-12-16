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

import java.io.IOException;
import java.util.concurrent.*;

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
        assertThat(EntityUtils.toString(resp.getEntity())).isEqualTo("Hello World!");
        assertThat(resp.getFirstHeader("x-hello").getValue()).isEqualTo("World!");
    }

}
