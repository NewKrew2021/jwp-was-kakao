package HttpRequest;

import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    Map<String, String> header;

    public RequestHeader(Map<String, String> header){
        this.header = header;
    }

    public Optional<Integer> getContentLength(){
        if(header.containsKey("Content-Length")){
            return Optional.of(Integer.parseInt(header.get("Content-Length")));
        }
        return Optional.empty();
    }
}
