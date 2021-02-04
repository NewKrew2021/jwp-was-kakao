package response;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "Location";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String HTML_TYPE = "text/html;charset=utf-8";
    private static final String CSS_TYPE = "text/css;charset=utf-8";
    private static final String JS_TYPE = "text/javascript;charset=utf-8";
    private DataOutputStream dos;
    private Map<String, String> header = new HashMap<>();
    private byte[] body;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }


    public void addHeader(String key, String value){
        header.put(key, value);
    }

    public void send(HttpResponseStatusCode responseStatusCode){
        //response header
        writeHeader(responseStatusCode);

        //response body
        writeBody();
    }
    private void writeHeader(HttpResponseStatusCode responseStatusCode) {
        try {
            this.dos.writeBytes(responseStatusCode.getMessage() + "\r\n");
            for (String key : this.header.keySet()) {
                this.dos.writeBytes(key + ": " + this.header.get(key) + "\r\n");
            }
            this.dos.writeBytes("\r\n");

        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }

    private void writeBody(){
        if(body == null){
            return;
        }
        try{
            this.dos.write(body, 0, body.length);
            this.dos.flush();
        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }

    public void addContentTypeHeader(String path){
        if(path.matches(".*\\.html")){
            addHeader(CONTENT_TYPE, HTML_TYPE);
            return;
        }
        if(path.matches(".*\\.css")){
            addHeader(CONTENT_TYPE, CSS_TYPE);
            return;
        }
        if(path.matches(".*\\.js")){
            addHeader(CONTENT_TYPE, JS_TYPE);
            return;
        }
    }

    public void addRedirectionLocationHeader(String location){
        addHeader(LOCATION, "http://localhost:8080" + location);
    }

    public void addContentLengthHeader(int lengthOfBodyContent){
        addHeader(CONTENT_LENGTH, Integer.toString(lengthOfBodyContent));
    }

    public void addSetCookieHeader(boolean isLogined){
        if(isLogined){
            addHeader(SET_COOKIE, "logined=true; Path=/");
        }
    }

    public void addResponseBody(byte[] body) {
        this.body = body;
    }

}
