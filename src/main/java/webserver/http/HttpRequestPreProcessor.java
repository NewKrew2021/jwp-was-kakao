package webserver.http;

/**
 * http request 가 controller 에 전달되기 전에 전처리 작업을 수행합니다.
 */
public interface HttpRequestPreProcessor {

    /**
     * PreProcessor 가 처리할 request 인지 판단합니다.
     *
     * @param request
     * @param response
     * @return
     */
    boolean matches(HttpRequest request, HttpResponse response);

    /**
     * matches 가 true 요청에 대해서 apply 가 실행된다.
     *
     * @param request
     */
    void apply(HttpRequest request);

    /**
     * 일치하는 ( match() 가 true ) 요청일때 apply() 가 실행됩니다.
     *
     * @param request
     */
    default void execute(HttpRequest request, HttpResponse response) {
        if (matches(request, response)) {
            apply(request);
        }
    }
}
