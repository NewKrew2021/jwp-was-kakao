package webserver;

@FunctionalInterface
public interface WebServerConfigurer {
    WebServerConfig apply(WebServerConfig config);
}
