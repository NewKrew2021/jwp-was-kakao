package webserver.http;

import java.util.Map;

public interface View {

    /**
     * view 를 http response message 로 변환하여 client 에게 보낸다
     *
     * @param model
     * @param httpRequest
     * @param httpResponse
     */
    void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse);

}
