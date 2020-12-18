package webserver;

import helper.TestHelper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import webserver.constant.HttpMethod;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class WebServerTest {

    private static final int DEFAULT_PORT = 8889;
    private static final String DEFAULT_HOST_URL = "http://localhost:" + DEFAULT_PORT;

    private static WebServer webServer = new WebServer();

    private static ExecutorService executorService;
    private static CloseableHttpClient httpClient;

    @BeforeAll
    public static void init() throws InterruptedException {
        Router router = webServer.getRouter();

        router.addRoute(HttpMethod.GET, "/hello", HelloController.getHelloHandler);

        CountDownLatch startSignal = webServer.getStartSignal();

        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            webServer.start(DEFAULT_PORT);
            return null;
        });

        startSignal.await();

        httpClient = TestHelper.testHttpClient().build();
    }

    @AfterAll
    public static void tearDown() {
        executorService.shutdown();
    }

    @Test
    public void helloWebServer() throws IOException {
        HttpGet httpGet = new HttpGet(DEFAULT_HOST_URL + "/hello");
        CloseableHttpResponse resp = httpClient.execute(httpGet);

        try {
            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
            assertThat(resp.getFirstHeader("x-hello").getValue()).isEqualTo("World!");

            assertThat(EntityUtils.toString(resp.getEntity())).isEqualTo("Hello World!");

        } finally {
            resp.close();
        }
    }

    @Test
    public void testNotFound() throws IOException {
        HttpGet httpGet = new HttpGet(DEFAULT_HOST_URL + "/not-found");
        CloseableHttpResponse resp = httpClient.execute(httpGet);

        try {
            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
        } finally {
            resp.close();
        }
    }

}
