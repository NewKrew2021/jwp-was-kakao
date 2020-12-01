package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseBuilder {

    public static HttpResponse build302Redirect(String location) {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.LOCATION, location);
        return new HttpResponse(HttpCode._302, headers);
    }
}
