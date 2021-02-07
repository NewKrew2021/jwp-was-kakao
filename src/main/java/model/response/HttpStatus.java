package model.response;

import java.util.HashMap;
import java.util.Map;

public enum HttpStatus {
    OK(200),
    CREATED(201),
    FOUND(302),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;
    HttpStatus(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
