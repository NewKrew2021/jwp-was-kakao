package response;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

class HttpResponseTest {

    private String testResources = "./src/test/resources/";


    @Test
    void forwardBody() throws FileNotFoundException {
        HttpResponse httpResponse = HttpResponse.from(createOutputStream("forwardBody.txt"));
        httpResponse.forwardBody(httpResponse.responseBody("/index.html"));
    }

    @Test
    void sendNewPage() throws FileNotFoundException {
        HttpResponse httpResponse = HttpResponse.from(createOutputStream("sendNewPage.txt"));
        httpResponse.sendNewPage("http://localhost:8080/user/login_failed.html", false);
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testResources + filename));
    }

}
