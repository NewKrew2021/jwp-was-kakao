package webserver.domain;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import webserver.domain.HttpResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HttpResponseTest {
    private String testDirectory = "src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        new HttpResponse.Builder()
                .status(HttpStatusCode.OK)
                .body("templates/index.html")
                .build()
                .sendResponse(createOutputStream("Http_Forward.txt"));
    }

    @Test
    public void responseRedirect() throws Exception {
        new HttpResponse.Builder()
                .status(HttpStatusCode.FOUND)
                .redirect("/index.html")
                .build()
                .sendResponse(createOutputStream("Http_Redirect.txt"));
    }

    @Test
    public void responseCookies() throws Exception {
        new HttpResponse.Builder()
                .status(HttpStatusCode.FOUND)
                .cookie(new Cookie("logined","true"))
                .redirect("/index.html")
                .build()
                .sendResponse(createOutputStream("Http_Cookie.txt"));
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
