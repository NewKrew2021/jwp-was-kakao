package app;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.FileUtils;
import webserver.constant.HttpHeader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {

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

        try {
            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
            assertThat(resp.getFirstHeader("x-hello").getValue()).isEqualTo("World!");

            assertThat(EntityUtils.toString(resp.getEntity())).isEqualTo("Hello World!");

        } finally {
            resp.close();
        }
    }

    // TODO not found test

    @Test
    public void helloStaticFile() throws IOException, URISyntaxException {
        String file = "/css/styles.css";

        HttpGet httpGet = new HttpGet(DEFAULT_HOST_URL + file);
        CloseableHttpResponse resp = httpClient.execute(httpGet);

        try {
            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
            assertThat(resp.getFirstHeader(HttpHeader.CONTENT_TYPE).getValue()).isEqualTo("text/css; charset=utf-8");

            String indexFilePath = "./static" + file;

            String actual = EntityUtils.toString(resp.getEntity());
            String expected = new String(FileUtils.loadFileFromClasspath(indexFilePath));

            assertThat(actual).isEqualTo(expected);
        } finally {
            resp.close();
        }
    }

    @Test
    public void testSignUpAndLogin() throws IOException {
        // signup
        HttpPost postSignUp = new HttpPost(DEFAULT_HOST_URL + "/user/create");

        postSignUp.setEntity(buildUrlEncodedFormEntity(
                new BasicNameValuePair("userId", "myuserid"),
                new BasicNameValuePair("password", "mypassword")));

        CloseableHttpResponse resp = httpClient.execute(postSignUp);

        try {
            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
            assertThat(resp.getFirstHeader(HttpHeader.LOCATION).getValue()).isEqualTo("/index.html");
        } finally {
            resp.close();
        }

        // login fail
        HttpPost postLoginFail = new HttpPost(DEFAULT_HOST_URL + "/user/login");

        postLoginFail.setEntity(buildUrlEncodedFormEntity(
                new BasicNameValuePair("userId", "not_my_id"),
                new BasicNameValuePair("password", "not_my_pw")));

        CloseableHttpResponse loginFailResp = httpClient.execute(postLoginFail);

        try {
            assertThat(loginFailResp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
            assertThat(loginFailResp.getFirstHeader(HttpHeader.LOCATION).getValue()).isEqualTo("/user/login_failed.html");
            assertThat(loginFailResp.getFirstHeader(HttpHeader.SET_COOKIE)).isNull();
        } finally {
            loginFailResp.close();
        }

        // login

        HttpPost postLogin = new HttpPost(DEFAULT_HOST_URL + "/user/login");

        postLogin.setEntity(buildUrlEncodedFormEntity(
                new BasicNameValuePair("userId", "myuserid"),
                new BasicNameValuePair("password", "mypassword")));

        CloseableHttpResponse loginResp = httpClient.execute(postLogin);

        try {
            assertThat(loginResp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
            assertThat(loginResp.getFirstHeader(HttpHeader.LOCATION).getValue()).isEqualTo("/index.html");
            assertThat(loginResp.getFirstHeader(HttpHeader.SET_COOKIE).getValue()).contains("logined=true");
        } finally {
            loginResp.close();
        }
    }

    private HttpEntity buildUrlEncodedFormEntity(NameValuePair... pair) {
        List<NameValuePair> params = Arrays.stream(pair).collect(Collectors.toList());
        return new UrlEncodedFormEntity(params, Consts.UTF_8);
    }

}
