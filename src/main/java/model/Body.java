package model;

public class Body {
    private byte[] body;

    public Body() {
        this.body = new byte[0];
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public int getLength() {
        return body.length;
    }

    public byte[] getBytes() {
        return body;
    }
}
