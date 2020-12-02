package webserver.http;

import java.util.List;

public class DefaultHttpRequestDispatcher implements HttpRequestDispatcher {

    private HttpRequestMapper<Controller> httpRequestMapper;

    public DefaultHttpRequestDispatcher(HttpRequestMapping... mappings) {
        httpRequestMapper = new DefaultHttpRequestMapper(mappings);
    }

    public DefaultHttpRequestDispatcher(List<HttpRequestMapping> mappings) {
        httpRequestMapper = new DefaultHttpRequestMapper(mappings);
    }

    public void dispatch(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = httpRequestMapper.getTarget(httpRequest);
        if( controller == null ) throw new NotFoundException(httpRequest.getPath());

        try {
            controller.execute(httpRequest, httpResponse);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpResponse.setStatus(HttpStatus.x500_InternalServerError);
        }
    }

}
