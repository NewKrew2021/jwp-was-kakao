package domain;

public class HttpBody {

    private final byte[] body;

    public HttpBody(byte[] body) {
        this.body = body;
    }

    public HttpBody(String body) {
        this.body = body.getBytes();
    }

    public int getBytesSize() {
        return body.length;
    }

    public byte[] getBytes() {
        return body;
    }

    @Override
    public String toString() {
        return new String(body);
    }
}
