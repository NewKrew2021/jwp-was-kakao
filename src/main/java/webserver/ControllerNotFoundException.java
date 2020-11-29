package webserver;

class ControllerNotFoundException extends RuntimeException {
    public ControllerNotFoundException(String uri) {
        super(String.format("Not Found: %s", uri));
    }
}
