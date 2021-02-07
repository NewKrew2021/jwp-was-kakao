package exception.http;

public class IllegalHttpRequestException extends RuntimeException{
    public IllegalHttpRequestException(){
        super("올바르지 않은 HTTP 요청입니다.");
    }
}
