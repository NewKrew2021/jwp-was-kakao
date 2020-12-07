package webserver;

import webserver.http.ExceptionHandler;
import webserver.http.HttpRequestDispatcher;
import webserver.http.HttpRequestPreProcessor;

public class WebServerConfig {

    private final int port;
    private HttpRequestDispatcher requestDispatcher;
    private HttpRequestPreProcessor requestPreProcessor;
    private ExceptionHandler exceptionHandler;

    private WebServerConfig(int port, HttpRequestDispatcher requestDispatcher, HttpRequestPreProcessor requestPreProcessor, ExceptionHandler exceptionHandler) {
        this.port = port;
        this.requestDispatcher = requestDispatcher;
        this.requestPreProcessor = requestPreProcessor;
        this.exceptionHandler = exceptionHandler;
    }

    public HttpRequestDispatcher getRequestDispatcher() {
        return requestDispatcher;
    }

    public HttpRequestPreProcessor getRequestPreProcessor() {
        return requestPreProcessor;
    }

    public int getPortOrDefault(int defaultPort) {
        return port < 0 ? defaultPort : port;
    }

    public static Builder builder(){
        return new Builder();
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public static class Builder {

        private int port;
        private HttpRequestDispatcher requestDispatcher;
        private HttpRequestPreProcessor preProcessor;
        private ExceptionHandler exceptionHandler;

        public WebServerConfig build(){
            return new WebServerConfig(port, requestDispatcher, preProcessor, exceptionHandler);
        }

        public Builder httpRequestDispatcher(HttpRequestDispatcher requestDispatcher){
            this.requestDispatcher = requestDispatcher;
            return this;
        }

        public Builder httpRequestPreProcessor(HttpRequestPreProcessor preProcessor){
            this.preProcessor = preProcessor;
            return this;
        }

        public Builder exceptionHandler(ExceptionHandler exceptionHandler){
            this.exceptionHandler = exceptionHandler;
            return this;
        }
        public Builder port(int port) {
            this.port = port;
            return this;
        }
    }
}
