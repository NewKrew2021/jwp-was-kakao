package webserver.http;

/**
 * RequestHandler 실행중 발생하는 exception 처리를 담당합니다.
 */
public interface ExceptionHandler<T extends Exception> {

    void handle(T e, HttpRequest httpRequest, HttpResponse httpResponse);

}
