package response;

import exceptions.InvalidResponseStatusCodeException;

import java.util.Arrays;

public enum HttpResponseStatusCode {
    OK(200, "HTTP/1.1 200 OK "),
    FOUND(302, "HTTP/1.1 302 Found "),
    BAD_REQUEST(400, "HTTP/1.1 400 Bad Request "),
    NOT_FOUND(404, "HTTP/1.1 404 Not-Found ");

    private int code;
    private String message;

    HttpResponseStatusCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static HttpResponseStatusCode of(int code){
        return Arrays.stream(HttpResponseStatusCode.values())
                .filter(StatusCode -> StatusCode.getCode() == code)
                .findFirst()
                .orElseThrow(InvalidResponseStatusCodeException::new);
    }
    
    public int getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
