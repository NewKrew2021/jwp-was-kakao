package domain;

public class HttpBody {

    private final String body;

    public HttpBody(byte[] body) {
        this.body = new String(body);
    }

    public HttpBody(String body) {
        this.body = body;
    }

    public int getBytesSize() {
        return body.getBytes().length;
    }

    public byte[] getBytes() {
        return body.getBytes();
    }

    @Override
    public String toString() {
        return body;
    }
}
