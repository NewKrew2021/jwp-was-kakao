package webserver.http;

import webserver.http.controller.Controller;

/**
 * HttpRequest 를 처리해줄 처리자를 찾아 request 를 전달한다. 처리자는 request 를 처리하고 response 까지 책임진다
 */
public class HttpRequestDispatcher {

    HttpRequestControllerMapper httpRequestControllerMapper = UriBaseHttpRequestControllerMapper.withDefaultMappings();

    public void dispatch(HttpRequest httpRequest, HttpResponse httpResponse){
        Controller controller = httpRequestControllerMapper.getController(httpRequest);
        controller.execute(httpRequest, httpResponse);
    }

}
