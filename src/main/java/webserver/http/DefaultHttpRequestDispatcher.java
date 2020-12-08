package webserver.http;

import java.util.Arrays;
import java.util.List;

public class DefaultHttpRequestDispatcher implements HttpRequestDispatcher {

    private HttpRequestMapper<Controller> requestMapper;
    private HttpResponseHandler responseHandler = new DefaultHttpResponseHandler();

    public DefaultHttpRequestDispatcher(HttpResponseHandler responseHandler, List<HttpRequestMapping> mappings){
        this.requestMapper = new DefaultHttpRequestMapper(mappings);
        this.responseHandler = responseHandler;
    }

    public DefaultHttpRequestDispatcher(HttpResponseHandler responseHandler, HttpRequestMapping... mappings){
        this(responseHandler, Arrays.asList(mappings));
    }

    public DefaultHttpRequestDispatcher(HttpRequestMapping... mappings) {
        this(new DefaultHttpResponseHandler(), mappings);
    }

    public DefaultHttpRequestDispatcher(List<HttpRequestMapping> mappings) {
        requestMapper = new DefaultHttpRequestMapper(mappings);
    }

    public void dispatch(HttpRequest request, HttpResponse response) {
        Controller controller = requestMapper.getTarget(request);
        if( controller == null ) throw new NotFoundException(request.getPath());

        ModelAndView mav = controller.execute(request, response);
        responseHandler.handle(mav, request, response);
    }

}
