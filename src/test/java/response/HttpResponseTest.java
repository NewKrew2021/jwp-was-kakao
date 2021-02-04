package response;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {

    private String testResources = "./src/test/resources/";

    @Test
    void response200Header() throws FileNotFoundException {
        HttpResponse response = HttpResponse.from(createOutputStream("response200Header.txt"));
        response.response200Header(30,"html");
    }

    @Test
    void response302HeaderTest1() throws FileNotFoundException {
        HttpResponse response = HttpResponse.from(createOutputStream("response302Header.txt"));
        response.response302Header("/user/login.html");
    }

    @Test
    public void response302HeaderTest2() throws Exception {
        HttpResponse response = HttpResponse.from(createOutputStream("response302Header.txt"));
        response.response302Header("/user/login.html", "false");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testResources + filename));
    }

}
