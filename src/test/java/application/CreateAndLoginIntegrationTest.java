package application;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.MemberService;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class CreateAndLoginIntegrationTest {

    private static final String TEST_DIRECTORY = "./src/test/resources/";
    private CreateUserController createUserController;
    private LoginController loginController;

    private HttpRequest request;
    private HttpResponse response;
    private OutputStream outputStream;

    private void setRequestAndResponseWithUrl(String url) throws IOException {
        outputStream = new ByteArrayOutputStream();
        response = new HttpResponse(new DataOutputStream(outputStream));
        InputStream in = new FileInputStream(TEST_DIRECTORY + url);
        request = HttpRequest.from(in);
    }

    @BeforeEach
    void setUp() {
        DataBase.clear();
        MemberService memberService = new MemberService();
        createUserController = new CreateUserController(memberService);
        loginController = new LoginController(memberService);
    }

    @Test
    void successTest() throws IOException {
        // given
        setRequestAndResponseWithUrl("create_user_sample.txt");
        createUserController.doPost(request, response);

        // when
        setRequestAndResponseWithUrl("login_user_sample.txt");
        loginController.doPost(request, response);

        // then
        String outputString = outputStream.toString();
        assertThat(outputString).contains("HTTP/1.1 302 Found");
        assertThat(outputString).contains("Set-Cookie: logined=true");
        assertThat(outputString).contains("Location: /index.html");
    }

    @Test
    void failTest() throws IOException {
        // given
        setRequestAndResponseWithUrl("create_user_sample.txt");
        createUserController.doPost(request, response);

        // when
        setRequestAndResponseWithUrl("login_user_sample_2.txt");
        loginController.doPost(request, response);

        // then
        String outputString = outputStream.toString();
        System.out.println(outputString);
        assertThat(outputString).contains("HTTP/1.1 302 Found");
        assertThat(outputString).contains("Set-Cookie: logined=false");
        assertThat(outputString).contains("Location: /user/login_failed.html");
    }
}