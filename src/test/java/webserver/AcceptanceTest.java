package webserver;

import db.DataBase;
import http.HttpRequest;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTest {
    @Test
    void addUser() throws Exception {
        String testDirectory = "./src/test/resources/";
        InputStream in = new FileInputStream(testDirectory + "Http_GET_Value.txt");
        HttpRequest request = HttpRequest.of(in);
        User user = User.of(request.getParameters());
        DataBase.addUser(user);

        assertThat(DataBase.findAll()).hasSize(1);
    }
}
