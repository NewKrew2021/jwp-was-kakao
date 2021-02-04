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
            addHeader("Content-Type", "text/html;charset=utf-8");
            return;
        }
        if(path.matches(".*\\.css")){
            addHeader("Content-Type", "text/css;charset=utf-8");
            return;
        }
        if(path.matches(".*\\.js")){
            addHeader("Content-Type", "text/javascript;charset=utf-8");
            return;
        }
    }

    public void addRedirectionLocationHeader(String location){
        addHeader("Location", "http://localhost:8080" + location);
    }

    public void addContentLengthHeader(int lengthOfBodyContent){
        addHeader("Content-Length", Integer.toString(lengthOfBodyContent));
    }

    public void addSetCookieHeader(boolean isLogined){
        if(isLogined){
            addHeader("Set-Cookie", "logined=true; Path=/");
        }
    }

    public void addResponseBody(byte[] body) {
        this.body = body;
    }

}
