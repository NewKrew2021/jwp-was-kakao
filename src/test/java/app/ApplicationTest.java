package app;

import helper.TestHelper;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.*;
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

    private static BasicCookieStore cookieStore;
    private static CloseableHttpClient httpCookieClient;    // rebuild for each test

    @BeforeAll
    public static void init() throws InterruptedException {
        startApplication();
    }

    @AfterAll
    public static void tearDown() {
        executorService.shutdown();
    }

    @BeforeEach
    public void before() {
        cookieStore = new BasicCookieStore();
        httpCookieClient = TestHelper.testHttpClient()
                .setDefaultCookieStore(cookieStore)
                .build();
    }

    @AfterEach
    public void after() throws IOException {
        httpCookieClient.close();
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

    @Test
    public void testStaticFile() throws IOException, URISyntaxException {
        String file = "/css/styles.css";

        HttpGet httpGet = new HttpGet(DEFAULT_HOST_URL + file);
        CloseableHttpResponse resp = httpCookieClient.execute(httpGet);

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
    public void testSignUpLoginAndList() throws IOException {
        String userId = "myuserid";
        String password = "mypassword";
        String email = "myemail@email.com";

        // signup
        HttpPost postSignUp = new HttpPost(DEFAULT_HOST_URL + "/user/create");

        postSignUp.setEntity(buildUrlEncodedFormEntity(
                new BasicNameValuePair("userId", userId),
                new BasicNameValuePair("password", password),
                new BasicNameValuePair("email", email)));

        CloseableHttpResponse signUpResp = httpCookieClient.execute(postSignUp);

        try {
            assertThat(signUpResp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
            assertThat(signUpResp.getFirstHeader(HttpHeader.LOCATION).getValue()).isEqualTo("/index.html");
        } finally {
            signUpResp.close();
        }

        // login fail
        HttpPost postLoginFail = new HttpPost(DEFAULT_HOST_URL + "/user/login");

        postLoginFail.setEntity(buildUrlEncodedFormEntity(
                new BasicNameValuePair("userId", "not_my_id"),
                new BasicNameValuePair("password", "not_my_pw")));

        CloseableHttpResponse loginFailResp = httpCookieClient.execute(postLoginFail);

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
                new BasicNameValuePair("userId", userId),
                new BasicNameValuePair("password", password)));

        CloseableHttpResponse loginResp = httpCookieClient.execute(postLogin);

        try {
            assertThat(loginResp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
            assertThat(loginResp.getFirstHeader(HttpHeader.LOCATION).getValue()).isEqualTo("/index.html");
            assertThat(loginResp.getFirstHeader(HttpHeader.SET_COOKIE).getValue()).contains("logined=true");
        } finally {
            loginResp.close();
        }

        // list
        HttpGet getList = new HttpGet(DEFAULT_HOST_URL + "/user/list");
        CloseableHttpResponse listResp = httpCookieClient.execute(getList);

        try {
            assertThat(listResp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);

            String body = EntityUtils.toString(listResp.getEntity());

            assertThat(body).contains(userId);
            assertThat(body).contains(email);
        } finally {
            listResp.close();
        }
    }

    @Test
    public void testNoLoginAndNoList() throws IOException {
        // list
        HttpGet getList = new HttpGet(DEFAULT_HOST_URL + "/user/list");
        CloseableHttpResponse listResp = httpCookieClient.execute(getList);

        try {
            assertThat(listResp.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);

            assertThat(listResp.getFirstHeader(HttpHeader.LOCATION).getValue()).isEqualTo("/user/login.html");
        } finally {
            listResp.close();
        }
    }

    private HttpEntity buildUrlEncodedFormEntity(NameValuePair... pair) {
        List<NameValuePair> params = Arrays.stream(pair).collect(Collectors.toList());
        return new UrlEncodedFormEntity(params, Consts.UTF_8);
    }

}
