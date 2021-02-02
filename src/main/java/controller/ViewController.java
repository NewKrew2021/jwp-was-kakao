package controller;

import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class ViewController extends Controller {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    {
        setBasePath("");
        putHandler("/js/.*", "GET", this::handleJs);
        putHandler("/css/.*", "GET", this::handleCss);
        putHandler("/.*", "GET", this::handleView);
    }

    public void handleCss(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("{}", request.getPath());
        byte[] body = FileIoUtils.loadFileFromClasspath("./static" + request.getPath());
        DataOutputStream dos = new DataOutputStream(out);
        HttpResponse.response200CssHeader(dos, body.length);
        HttpResponse.responseBody(dos, body);
    }

    public void handleJs(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("{}", request.getPath());
        byte[] body = FileIoUtils.loadFileFromClasspath("./static" + request.getPath());
        DataOutputStream dos = new DataOutputStream(out);
        HttpResponse.response200JsHeader(dos, body.length);
        HttpResponse.responseBody(dos, body);
    }

    public void handleView(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" +
                (request.getPath().equals("/") ? "/index.html" : request.getPath()));
        DataOutputStream dos = new DataOutputStream(out);
        HttpResponse.response200Header(dos, body.length);
        HttpResponse.responseBody(dos, body);
    }
}
