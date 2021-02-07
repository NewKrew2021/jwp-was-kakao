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

public class UserListControllerTest {
    private String testDirectory = "./src/test/resources/controller";
    private DataOutputStream dos;
    private ByteArrayOutputStream bos;
    private UserListController userListController;

    @BeforeEach
    void init(){
        userListController = new UserListController();
        bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);

        DataBase.deleteAll();
        User user1 = new User("javajigi", "abc", "name", "email");
        User user2 = new User("javajigi2", "123", "name", "email");
        DataBase.addUser(user1);
        DataBase.addUser(user2);
    }

    @DisplayName("쿠키가 true면 유저 리스트를 반환한다.")
    @Test
    void userList() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_UserList.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        userListController.service(httpRequest, httpResponse);

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
