package dto;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private String status;
    private Map<String, String> headers = new HashMap<>();
    private byte[] body = new byte[0];

    public void setBody(byte[] body){
        this.body = body;
        this.headers.put("Content-Type", "text/html;charset=utf-8");
        this.headers.put("Content-Length", "" + body.length);
    }

    public HttpResponse(byte[] body) {
        setBody(body);
    }

    public HttpResponse(String status) {
        this.status = status;
    }

    public HttpResponse(String status, byte[] body) {
        this(body);
        this.status = status;
    }

    public HttpResponse(String status, Map<String, String> headers) {
        this.status = status;
        this.headers = headers;
    }

    public String getHeaders() {
        StringBuilder message = new StringBuilder();
        message.append(status + "\r\n");

        for (Map.Entry<String, String> header : headers.entrySet()) {
            message.append(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        message.append("\r\n");

        return String.valueOf(message);
    }

    public byte[] getBody() {
        return body;
    }
}
