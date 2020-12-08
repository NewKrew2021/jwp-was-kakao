package webserver.http;

public class HttpResponse {
    private ResponseStatus status;
    private final ResponseHeader header;
    private final byte[] body;

    public HttpResponse() {
        this.header = new ResponseHeader();
        this.body = "".getBytes();
    }

    public HttpResponse(byte[] body) {
        this.status = ResponseStatus.OK;
        this.header = new ResponseHeader();
        this.body = body;
    }

    public HttpResponse(ResponseHeader header) {
        this.status = ResponseStatus.OK;
        this.header = header;
        this.body = "".getBytes();
    }

    public HttpResponse(ResponseHeader header, byte[] body) {
        this.status = ResponseStatus.OK;
        this.header = header;
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public HttpResponse redirect(String path){
        this.status = ResponseStatus.FOUND;
        this.header.addHeader("Location", path);
        return this;
    }

    public HttpResponse error(){
        this.status = ResponseStatus.NOT_FOUND;
        return this;
    }
}
