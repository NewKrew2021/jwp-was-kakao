package webserver;

import webserver.http.ExceptionHandler;
import webserver.http.HttpRequestMapping;
import webserver.http.HttpRequestPreProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServerConfig {

    private int port;
    private HttpRequestPreProcessor requestPreProcessor;
    private boolean enableSession = false;
    private List<HttpRequestMapping> requestMappings = new ArrayList<>();
    private Map<Class, ExceptionHandler> exceptionHandlers = new HashMap<>();

    private WebServerConfig() {
    }

    @Deprecated
    private WebServerConfig(int port,
                            HttpRequestPreProcessor requestPreProcessor) {
        this.port = port;
        this.requestPreProcessor = requestPreProcessor;
    }

    public static WebServerConfig configurer(WebServerConfigurer configurer) {
        return configurer.apply(new WebServerConfig());
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setRequestPreProcessor(HttpRequestPreProcessor requestPreProcessor) {
        this.requestPreProcessor = requestPreProcessor;
    }

    public Map<Class, ExceptionHandler> getExceptionHandlers() {
        return exceptionHandlers;
    }

    public List<HttpRequestMapping> getRequestMappings() {
        return requestMappings;
    }

    public void setEnableSession(boolean enableSession) {
        this.enableSession = enableSession;
    }

    public HttpRequestPreProcessor getRequestPreProcessor() {
        return requestPreProcessor;
    }

    public int getPortOrDefault(int defaultPort) {
        return port < 0 ? defaultPort : port;
    }

    public boolean isEnableSession() {
        return enableSession;
    }

    public void setRequestMappings(List<HttpRequestMapping> requestMappings) {
        this.requestMappings = requestMappings;
    }

    public void setExceptionHandlers(Map<Class, ExceptionHandler> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
    }
}
