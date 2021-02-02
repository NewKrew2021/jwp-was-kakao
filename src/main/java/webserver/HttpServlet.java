package webserver;

import web.HttpRequest;
import web.HttpResponse;

public interface HttpServlet {
    HttpResponse service(HttpRequest httpRequest);

    boolean isSupport(HttpRequest httpRequest);
}
