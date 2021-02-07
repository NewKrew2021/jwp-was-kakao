package exception.http;

public class IllegalHttpResponseException extends RuntimeException{
    public IllegalHttpResponseException(){
        super("잘못된 Http Response 입니다");
    }
}
