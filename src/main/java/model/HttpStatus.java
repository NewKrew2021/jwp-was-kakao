package model;

import java.util.HashMap;
import java.util.Map;

public class HttpStatus {
    private static final Map<Integer, String> httpStatusCode = new HashMap<>();
    static {
        httpStatusCode.put(200, "OK");
        httpStatusCode.put(201, "CREATED");
        httpStatusCode.put(302, "FOUND");
        httpStatusCode.put(400, "BAD REQUEST");
        httpStatusCode.put(401, "Unauthorized");
        httpStatusCode.put(404, "NOT FOUND");
        httpStatusCode.put(500, "INTERNAL SERVER ERROR");
    }

    public String get(Integer key){
        return httpStatusCode.get(key);
    }
}
