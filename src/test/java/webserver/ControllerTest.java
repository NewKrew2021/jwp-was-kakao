package webserver;

import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerTest {
    private String testDirectory = "./src/test/resources/controller";
    private DataOutputStream dos;
    private ByteArrayOutputStream bos;

    @BeforeEach
    void init(){
        bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);

        DataBase.deleteAll();
        User user1 = new User("javajigi", "abc", "name", "email");
        User user2 = new User("javajigi2", "123", "name", "email");
        DataBase.addUser(user1);
        DataBase.addUser(user2);
    }

    @DisplayName("새로운 유저를 생성한 후 index.html로 redirect한다.")
    @Test
    void userCreate() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Create.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserCreateController().service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_Create_Response.txt");
        assertThat(DataBase.findUserById("test")).isNotEmpty();
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @DisplayName("id, pw가 일치하면 쿠키를 true로 설정한다.")
    @Test
    void userLogin_success() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserLoginController().service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_Login_Response.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @DisplayName("id에 해당하는 유저가 없으면 쿠키를 false로 설정한다.")
    @Test
    void userLogin_fail_wrongId() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login_Fail_WrongId.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserLoginController().service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_Login_Fail_WrongId_Response.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @DisplayName("id, pw가 일치하지 않으면 쿠키를 false로 설정한다.")
    @Test
    void userLogin_fail_wrongPassword() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login_Fail_WrongPassword.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserLoginController().service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_Login_Fail_WrongPassword_Response.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @DisplayName("쿠키가 true면 유저 리스트를 반환한다.")
    @Test
    void userList() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_UserList.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserListController().service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_UserList_Response.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @DisplayName("쿠키가 false면 로그인 페이지로 redirect한다.")
    @Test
    void userList_cookie_fail() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_UserList_Cookie_Fail.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserListController().service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_UserList_Fail_Response.txt");
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @DisplayName("쿠키가 없으면 로그인 페이지로 redirect한다.")
    @Test
    void userList_cookie_null() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_UserList_Cookie_Null.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserListController().service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_UserList_Fail_Response.txt");
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
