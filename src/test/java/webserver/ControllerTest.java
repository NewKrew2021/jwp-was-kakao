package webserver;

import controller.UserCreateController;
import controller.UserLoginController;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;

public class ControllerTest {
    private String testDirectory = "./src/test/resources/controller";

    @Test
    void userCreate() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Create.txt"));
        HttpResponse httpResponse = new HttpResponse(createOutputStream("/Http_Create_Response.txt"));

        new UserCreateController().service(httpRequest, httpResponse);
    }

    //Login
    @Test
    void userLogin_success() throws IOException, URISyntaxException {
        User user = new User("javajigi", "password", "name", "email");
        DataBase.addUser(user);
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login.txt"));
        HttpResponse httpResponse = new HttpResponse(createOutputStream("/Http_Login_Response.txt"));

        new UserLoginController().service(httpRequest, httpResponse);
    }

    @Test
    void userLogin_fail_wrongId() throws IOException, URISyntaxException {
        User user = new User("test", "password", "name", "email");
        DataBase.addUser(user);
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login.txt"));
        HttpResponse httpResponse = new HttpResponse(createOutputStream("/Http_Login_Fail_WrongId.txt"));

        new UserLoginController().service(httpRequest, httpResponse);
    }

    @Test
    void userLogin_fail_wrongPassword() throws IOException, URISyntaxException {
        User user = new User("javajigi", "abc", "name", "email");
        DataBase.addUser(user);
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Login.txt"));
        HttpResponse httpResponse = new HttpResponse(createOutputStream("/Http_Login_Fail_WrongPassword.txt"));

        new UserLoginController().service(httpRequest, httpResponse);
    }

    private BufferedReader createBufferedReader(String filename) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + filename));
        return new BufferedReader(new InputStreamReader(in));
    }

    private DataOutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new DataOutputStream(new FileOutputStream(new File(testDirectory + filename)));
    }

    //List

}
