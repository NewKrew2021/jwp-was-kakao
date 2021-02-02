package request;

import java.util.Map;

public class RequestBody {
    Map<String, String> body;

    public RequestBody(Map<String, String> body){
        this.body = body;
    }

    public Map<String, String> getBody() {
        return body;
    }
}
