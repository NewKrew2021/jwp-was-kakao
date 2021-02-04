package webserver.domain;

public class RequestBody {
    private final Parameters parameters;

    public RequestBody(Parameters parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String key, String defaultValue) {
        return parameters.get(key, defaultValue);
    }
}
