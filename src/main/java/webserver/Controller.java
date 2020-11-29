package webserver;

@FunctionalInterface
interface Controller {
    Response execute(HttpRequest httpRequest);

}
