package response;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import exception.ResponseCreateFailException;
import model.User;
import utils.FileIoUtils;
import utils.HttpResponseUtils;
import utils.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HttpResponse {

    private static final String SET_COOKIE = "Set-Cookie";
    public static final String HTTP_1_1 = "HTTP/1.1";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";

    private DataOutputStream dos;
    private HttpStatus httpStatus;
    private List<String> headers;
    private String contentType;

    private HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        this.httpStatus = HttpStatus.OK;
        this.headers = new ArrayList<>();
    }

    public static HttpResponse from(OutputStream out) {
        return new HttpResponse(new DataOutputStream(out));
    }

    public void forwardBody(byte[] body) {
        if (contentType != null) {
            addHeader(CONTENT_TYPE, contentType);
        }
        addHeader(CONTENT_LENGTH, String.valueOf(body.length));
        try {
            writeResponseHeader();
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            throw new ResponseCreateFailException();
        }
    }

    public byte[] responseBody(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath(HttpResponseUtils.findPath(path));
        } catch (IOException | URISyntaxException e) {
            throw new ResponseCreateFailException();
        }
    }

    public byte[] responseTemplateBody(Collection<User> users) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile("user/list");
            String result = template.apply(users);
            result = IOUtils.decodeData(result);
            return result.getBytes("UTF-8");
        } catch (IOException e) {
            throw new ResponseCreateFailException();
        }
    }

    public void sendNewPage(String newPage, boolean login) {
        this.httpStatus = HttpStatus.FOUND;
        addHeader(SET_COOKIE, HttpResponseUtils.makeLoginCookie(login));
        sendRedirect(newPage);
    }

    private void sendRedirect(String location) {
        addHeader(LOCATION, location);
        try {
            writeResponseHeader();
        } catch (IOException e) {
            throw new ResponseCreateFailException();
        }
    }

    private void writeResponseHeader() throws IOException {
        dos.writeBytes(String.format("%s %s \r\n", HTTP_1_1, httpStatus.toString()));
        for (String header : headers) {
            dos.writeBytes(String.format("%s \r\n", header));
        }
        dos.writeBytes("\r\n");
    }

    private void addHeader(String key, String value) {
        headers.add(key + ": " + value);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
