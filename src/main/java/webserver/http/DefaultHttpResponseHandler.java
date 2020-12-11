package webserver.http;

public class DefaultHttpResponseHandler implements HttpResponseHandler {

    private HttpResponsePreProcessor responsePreProcessor;

    public DefaultHttpResponseHandler() {
        this.responsePreProcessor = HttpResponsePreProcessor.NOOP;
    }

    public DefaultHttpResponseHandler(HttpResponsePreProcessor responsePreProcessor) {
        this.responsePreProcessor = responsePreProcessor;
    }

    /**
     * * view 를 보고 resource 를 읽어와서 model 을 가지고 response message 를 만든다
     * * httpResponse 를 사용해서 view 를 client 에 보낸다
     *
     * @param modelAndView
     * @param httpRequest
     * @param httpResponse
     */
    @Override
    public void handle(ModelAndView modelAndView, HttpRequest httpRequest, HttpResponse httpResponse) {
        responsePreProcessor.execute(httpRequest, httpResponse);
        View view = modelAndView.getView();
        view.render(modelAndView.getModel(), httpRequest, httpResponse);
    }

}
