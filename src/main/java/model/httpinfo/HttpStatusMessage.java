package model.httpinfo;

import exception.http.IllegalStatusCodeException;

import java.util.HashMap;
import java.util.Map;

public class HttpStatusMessage {
    private static final Map<Integer, String> httpStatusCode = new HashMap<>();

    static {
        httpStatusCode.put(200, "OK");
        httpStatusCode.put(201, "CREATED");
        httpStatusCode.put(302, "FOUND");
        httpStatusCode.put(400, "BAD REQUEST");
        httpStatusCode.put(401, "UNAUTHORIZED");
        httpStatusCode.put(404, "NOT FOUND");
        httpStatusCode.put(500, "INTERNAL SERVER ERROR");
    }

    public static String of(int code) {
        String message = httpStatusCode.get(code);
        if (message == null) {
            throw new IllegalStatusCodeException();
        }
        return message;
    }
}
