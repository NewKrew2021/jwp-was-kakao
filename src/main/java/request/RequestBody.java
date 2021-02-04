package request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static utils.HttpRequestUtils.requestStringToMap;

public class RequestBody {
    Map<String, String> body;

    private RequestBody() {
    }

    private RequestBody(Map<String, String> body) {
        this.body = body;
    }

    public static RequestBody of(BufferedReader br, Optional<Integer> contentLength) throws IOException {
        if (contentLength.isPresent()) {
            return new RequestBody(requestStringToMap(IOUtils.readData(br, contentLength.get())));
        }
        return new RequestBody();
    }

    public Optional<String> getBodyValue(String key) {
        if (body.containsKey(key)) {
            return Optional.of(body.get(key));
        }
        return Optional.empty();
    }

    public Map<String, String> getBody() {
        return body;
    }

}
