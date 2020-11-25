package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;

    private String statusLine = "HTTP/1.1 200 OK";
    private List<HttpHeader> headers;
    private byte[] body;
    private HttpStatus status;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.headers = new ArrayList<>();
    }

    public void setBody(byte[] body) {
        this.body = body;
        addHeader("Content-Length", String.valueOf(body.length));
    }

    public void setStatus(HttpStatus status){
        this.status = status;
    }

    public String getStatusLine(){
        return "HTTP/1.1 " + status;
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public void addHeader(String key, String value) {
        this.headers.add(new HttpHeader(key,value));
    }

    public void setContentType(String contentType){
        headers.add(new HttpHeader("Content-Type", contentType) );
    }

    public void send(){
        try {
            writeHeader();
            writeBody();
            flush();
        } catch ( IOException e ){
            throw new RuntimeException("Http 응답메세지를 보내는과정에 문제가 발생했습니다", e);
        }

    }

    private void writeHeader() throws IOException {
        dos.writeBytes( getStatusLine() + " \r\n");
        for( HttpHeader header : headers ){
            dos.writeBytes(header.toString() + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void writeBody() throws IOException {
        if( body != null && body.length > 0 )
            dos.write(body, 0, body.length);
    }

    private void flush() throws IOException {
        dos.flush();
    }

}
