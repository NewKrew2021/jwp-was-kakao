package webserver.http.dispatcher;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

/**
 * HttpRequest 를 처리해줄 처리자를 찾아 request 를 전달한다. 처리자는 request 를 처리하고 response 까지 책임진다
 */
public interface HttpRequestDispatcher {

    void dispatch(HttpRequest httpRequest, HttpResponse httpResponse);

}
