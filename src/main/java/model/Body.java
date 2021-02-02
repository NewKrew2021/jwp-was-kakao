package model;

public class Body {
    private byte[] body;

    public Body(byte[] body){
        this.body=body;
    }

    public int getLength() {
        return body.length;
    }

    public byte[] getBytes(){
        return body;
    }
}
