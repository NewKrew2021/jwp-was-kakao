package webserver;

public class Response {

    private final ResponseHeader header;
    private final byte[] body;

    public Response(ResponseHeader header) {
        this.header = header;
        this.body = "".getBytes();
    }

    public Response(ResponseHeader header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header.toString();
    }

    public byte[] getBody() {
        return body;
    }
}
