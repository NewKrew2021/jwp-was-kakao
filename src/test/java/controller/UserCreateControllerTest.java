package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateControllerTest {

    private String testDirectory = "./src/test/resources/controller";
    private DataOutputStream dos;
    private ByteArrayOutputStream bos;
    private UserCreateController userCreateController;

    @BeforeEach
    void init(){
        bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);
        userCreateController = new UserCreateController();
    }

    @DisplayName("새로운 유저를 생성한 후 index.html로 redirect한다.")
    @Test
    void userCreate() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(createBufferedReader("/Http_Create.txt"));
        HttpResponse httpResponse = new HttpResponse(dos);

        userCreateController.service(httpRequest, httpResponse);

        String expected = readResponseFile("/Http_Create_Response.txt");
        assertThat(DataBase.findUserById("test")).isNotEmpty();
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
