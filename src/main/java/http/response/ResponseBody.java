package http.response;

import java.nio.charset.StandardCharsets;

public class ResponseBody {
    private final String body;

    public ResponseBody(String body) {
        this.body = body;
    }

    public ResponseBody(byte[] bytes){
        this.body = new String(bytes, StandardCharsets.UTF_8);
    }

    public static ResponseBody ofEmptyBody(){
        return new ResponseBody("");
    }

    public int getByteSize(){
        return body.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public String toString(){
        return body;
    }
}
