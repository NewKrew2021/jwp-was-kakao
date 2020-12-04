package webserver.http;

/**
 * controller 에서 비지니스 처리를 끝내고 난 후 client 에게 전달할 HttpResponse message 처리를 담당 합니다.
 *
 * model - view 가 구성할 message 에 필요한 data 를 제공합니다.
 * view - client 에서 전달할 message 의 형태를 결정합니다.
 *
 */
public interface HttpResponseHandler {

    void handle(ModelAndView modelAndView, HttpRequest httpRequest, HttpResponse httpResponse);

}
