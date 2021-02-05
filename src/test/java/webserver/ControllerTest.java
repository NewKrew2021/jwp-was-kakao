package webserver;

import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;
import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerTest {
    private String testDirectory = "./src/test/resources/controller";
    private DataOutputStream dos;
    private ByteArrayOutputStream bos;

    @BeforeEach
    void init(){
        bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);

        User user1 = new User("javajigi", "abc", "name", "email");
        User user2 = new User("javajigi2", "123", "name", "email");
        DataBase.addUser(user1);
        DataBase.addUser(user2);
    }

    @Test
    void userCreate() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Create.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserCreateController().service(httpRequest, httpResponse);
        Path path = Paths.get(getClass().getClassLoader().getResource("controller/Http_Create_Response.txt").toURI());
        String expected = new String(Files.readAllBytes(path));
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @Test
    void userLogin_success() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserLoginController().service(httpRequest, httpResponse);
        Path path = Paths.get(getClass().getClassLoader().getResource("controller/Http_Login_Response.txt").toURI());
        String expected = new String(Files.readAllBytes(path));
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @Test
    void userLogin_fail_wrongId() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login_Fail_WrongId.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserLoginController().service(httpRequest, httpResponse);
        Path path = Paths.get(getClass().getClassLoader().getResource("controller/Http_Login_Fail_WrongId_Response.txt").toURI());
        String expected = new String(Files.readAllBytes(path));
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @Test
    void userLogin_fail_wrongPassword() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login_Fail_WrongPassword.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserLoginController().service(httpRequest, httpResponse);
        Path path = Paths.get(getClass().getClassLoader().getResource("controller/Http_Login_Fail_WrongPassword_Response.txt").toURI());
        String expected = new String(Files.readAllBytes(path));
        assertThat(bos.toString()).isEqualTo(expected);
    }

    private BufferedReader createBufferedReader(String filename) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + filename));
        return new BufferedReader(new InputStreamReader(in));
    }

    @Test
    void userList() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_UserList.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserListController().service(httpRequest, httpResponse);
        Path path = Paths.get(getClass().getClassLoader().getResource("controller/Http_UserList_Response.txt").toURI());
        String expected = new String(Files.readAllBytes(path));
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @Test
    void userList_cookie_fail() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_UserList_Cookie_Fail.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserListController().service(httpRequest, httpResponse);
        Path path = Paths.get(getClass().getClassLoader().getResource("controller/Http_UserList_Fail_Response.txt").toURI());
        String expected = new String(Files.readAllBytes(path));
        assertThat(bos.toString()).isEqualTo(expected);
    }

    @Test
    void userList_cookie_null() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_UserList_Cookie_Null.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        new UserListController().service(httpRequest, httpResponse);
        Path path = Paths.get(getClass().getClassLoader().getResource("controller/Http_UserList_Fail_Response.txt").toURI());
        String expected = new String(Files.readAllBytes(path));
        assertThat(bos.toString()).isEqualTo(expected);
    }
}
