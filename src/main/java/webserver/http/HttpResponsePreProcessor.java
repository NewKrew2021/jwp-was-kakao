package webserver.http;

public interface HttpResponsePreProcessor {
    /**
     * PostProcessor 가 처리할 response 인지 판단합니다.
     *
     * @param request
     * @return
     */
    boolean matches(HttpRequest request, HttpResponse response);

    /**
     * matches 가 true 요청에 대해서 apply 가 실행된다.
     *
     * @param response
     */
    void apply(HttpResponse response);

    /**
     * 일치하는 ( match() 가 true ) 요청일때 apply() 가 실행됩니다.
     *
     * @param request
     */
    default void execute(HttpRequest request, HttpResponse response) {
        if (matches(request, response)) {
            apply(response);
        }
    }

    HttpResponsePreProcessor NOOP = new HttpResponsePreProcessor() {
        @Override
        public boolean matches(HttpRequest request, HttpResponse response) {
            return false;
        }

        @Override
        public void apply(HttpResponse response) {
            // NOOP
        }
    };


}
