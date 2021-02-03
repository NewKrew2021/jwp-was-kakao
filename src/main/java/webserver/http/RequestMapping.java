package webserver.http;

public interface RequestMapping {
    Controller getController(String path);
}
