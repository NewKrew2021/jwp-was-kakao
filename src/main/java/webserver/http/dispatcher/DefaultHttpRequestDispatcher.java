package webserver.http.dispatcher;

import webserver.http.*;

import java.util.List;

public class DefaultHttpRequestDispatcher implements HttpRequestDispatcher {

    private HttpRequestControllerMapper httpRequestControllerMapper;

    public DefaultHttpRequestDispatcher(HttpRequestMapping... mappings){
        httpRequestControllerMapper = new UriBaseHttpRequestControllerMapper(mappings);
    }

    public DefaultHttpRequestDispatcher(List<HttpRequestMapping> mappings){
        httpRequestControllerMapper = new UriBaseHttpRequestControllerMapper(mappings);
    }

    public void dispatch(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = httpRequestControllerMapper.getController(httpRequest);
        try {
            controller.execute(httpRequest, httpResponse);
        } catch (RuntimeException e) {
            httpResponse.setStatus(HttpStatus.x500_InternalServerError);
        }
    }

}
