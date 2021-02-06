package service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import service.db.DataBase;
import utils.FileIoUtils;
import webserver.WebServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {

    private static final String baseUrl = "http://localhost:8080";

    @BeforeAll
    void setUp() {
        Thread t = new Thread(() -> {
            try {
                WebServer.main(new String[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    @Test
    @DisplayName("index.html에 GET 요청을 보낸다.")
    void GetIndexTest() throws Exception {
        HttpUriRequest request = new HttpGet(baseUrl + "/index.html");

        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String responseContent = EntityUtils.toString(httpResponse.getEntity());
        String fileContent = new String(FileIoUtils.loadFileFromClasspath("./templates/index.html"));

        assertThat(httpResponse.getCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(responseContent).isEqualTo(fileContent);
    }

    @Test
    @DisplayName("회원가입을 하고 회원이 추가되는 것을 확인한다.")
    void PostRegisterTest() throws Exception {
        CloseableHttpResponse httpResponse = register();

        assertThat(httpResponse.getCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
        assertThat(DataBase.findUserById("brody")).isNotNull();
    }

    @Test
    @DisplayName("로그인에 성공했을 때 location과 set-cookie를 확인한다")
    void LoginSuccessTest() throws Exception {
        register();

        HttpUriRequest request = new HttpPost(baseUrl + "/user/login");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("userId", "brody"));
        nvps.add(new BasicNameValuePair("password", "1234"));
        request.setEntity(new UrlEncodedFormEntity(nvps));

        CloseableHttpResponse httpResponse = HttpClientBuilder.create()
                .disableRedirectHandling()
                .build()
                .execute(request);

        assertThat(httpResponse.getCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
        assertThat(Arrays.stream(httpResponse.getHeaders())
                .filter(header -> header.getName().equals("Location"))
                .map(NameValuePair::getValue)
                .findFirst()
                .orElse(null)).isEqualTo("/index.html");
        assertThat(Arrays.stream(httpResponse.getHeaders())
                .filter(header -> header.getName().equals("Set-Cookie"))
                .map(NameValuePair::getValue)
                .findFirst()
                .orElse(null)).contains("logined=true; Path=/");
    }

    @Test
    @DisplayName("로그인에 실패했을 때 location과 set-cookie를 확인한다")
    void LoginFailTest() throws Exception {
        HttpUriRequest request = new HttpPost(baseUrl + "/user/login");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("userId", "brody"));
        nvps.add(new BasicNameValuePair("password", "2345"));
        request.setEntity(new UrlEncodedFormEntity(nvps));

        CloseableHttpResponse httpResponse = HttpClientBuilder.create()
                .disableRedirectHandling()
                .build()
                .execute(request);

        assertThat(httpResponse.getCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
        assertThat(Arrays.stream(httpResponse.getHeaders())
                .filter(header -> header.getName().equals("Location"))
                .map(NameValuePair::getValue)
                .findFirst()
                .orElse(null)).isEqualTo("/user/login_failed.html");
        assertThat(Arrays.stream(httpResponse.getHeaders())
                .filter(header -> header.getName().equals("Set-Cookie"))
                .map(NameValuePair::getValue)
                .findFirst()
                .orElse(null)).contains("logined=false");
    }

    private static CloseableHttpResponse register() throws Exception {
        HttpUriRequest request = new HttpPost(baseUrl + "/user/create");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("userId", "brody"));
        nvps.add(new BasicNameValuePair("password", "1234"));
        nvps.add(new BasicNameValuePair("name", "kim"));
        nvps.add(new BasicNameValuePair("email", "abc@abc.com"));
        request.setEntity(new UrlEncodedFormEntity(nvps));
        return HttpClientBuilder.create()
                .disableRedirectHandling()
                .build()
                .execute(request);
    }

}
