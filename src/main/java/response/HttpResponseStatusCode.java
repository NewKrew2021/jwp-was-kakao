package response;

import exceptions.InvalidResponseStatusCodeException;

public enum HttpResponseStatusCode {
    OK(200, "HTTP/1.1 200 OK "),
    FOUND(302, "HTTP/1.1 302 Found ");

    private int code;
    private String message;

    HttpResponseStatusCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static HttpResponseStatusCode of(int code){
        if(code == 200){
            return OK;
        }
        if(code == 302){
            return FOUND;
        }
        throw new InvalidResponseStatusCodeException();
    }

    public String getMessage(){
        return this.message;
    }
}
