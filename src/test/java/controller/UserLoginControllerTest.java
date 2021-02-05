package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserLoginControllerTest {
    private String testDirectory = "./src/test/resources/controller";
    private DataOutputStream dos;
    private ByteArrayOutputStream bos;
    private UserLoginController userLoginController;

    @BeforeEach
    void init(){
        userLoginController = new UserLoginController();
        bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);

        DataBase.deleteAll();
        User user1 = new User("javajigi", "abc", "name", "email");
        User user2 = new User("javajigi2", "123", "name", "email");
        DataBase.addUser(user1);
        DataBase.addUser(user2);
    }

    @DisplayName("id, pw가 일치하면 쿠키를 true로 설정한다.")
    @Test
    void userLogin_success() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        userLoginController.service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_Login_Response.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @DisplayName("id에 해당하는 유저가 없으면 쿠키를 false로 설정한다.")
    @Test
    void userLogin_fail_wrongId() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login_Fail_WrongId.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        userLoginController.service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_Login_Fail_WrongId_Response.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @DisplayName("id, pw가 일치하지 않으면 쿠키를 false로 설정한다.")
    @Test
    void userLogin_fail_wrongPassword() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login_Fail_WrongPassword.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        userLoginController.service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_Login_Fail_WrongPassword_Response.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    private BufferedReader createBufferedReader(String filename) throws IOException {
        InputStream in = new FileInputStream(testDirectory + filename);
        return new BufferedReader(new InputStreamReader(in));
    }

    private String readResponseFile(String filename) throws IOException {
        InputStream in = new FileInputStream(testDirectory + filename);
        return new String(in.readAllBytes());
    }
}
