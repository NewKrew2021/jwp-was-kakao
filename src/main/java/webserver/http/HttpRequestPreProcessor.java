package webserver.http;

/**
 * http request 가 controller 에 전달되기 전에 전처리 작업을 수행합니다.
 */
public interface HttpRequestPreProcessor {

    /**
     * PreProcessor 가 처리할 request 인지 판단합니다.
     * @param httpRequest
     * @return
     */
    boolean matches(HttpRequest httpRequest);

    /**
     * matches 가 true 요청에 대해서 apply 가 실행된다.
     * @param httpRequest
     * @param httpResponse
     */
    void apply(HttpRequest httpRequest, HttpResponse httpResponse);

    /**
     * 일치하는 ( match() 가 true ) 요청일때 apply() 가 실행됩니다.
     *
     * @param httpRequest
     * @param httpResponse
     */
    default void execute(HttpRequest httpRequest, HttpResponse httpResponse){
        if( matches(httpRequest) ){
            apply(httpRequest, httpResponse);
        }
    }
}
