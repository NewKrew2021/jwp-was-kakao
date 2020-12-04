package webserver.http;

import java.util.List;

public class DefaultHttpRequestDispatcher implements HttpRequestDispatcher {

    private HttpRequestMapper<Controller> httpRequestMapper;
    private HttpResponseHandler httpResponseHandler = new DefaultHttpResponseHandler();

    public DefaultHttpRequestDispatcher(HttpRequestMapping... mappings) {
        httpRequestMapper = new DefaultHttpRequestMapper(mappings);
    }

    public DefaultHttpRequestDispatcher(List<HttpRequestMapping> mappings) {
        httpRequestMapper = new DefaultHttpRequestMapper(mappings);
    }

    public void dispatch(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = httpRequestMapper.getTarget(httpRequest);
        if( controller == null ) throw new NotFoundException(httpRequest.getPath());

        ModelAndView mav = controller.execute(httpRequest, httpResponse);
        httpResponseHandler.handle(mav, httpRequest, httpResponse);
    }

}
