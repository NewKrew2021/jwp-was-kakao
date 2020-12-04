package webserver;

import webserver.http.HttpRequestDispatcher;
import webserver.http.HttpRequestPreProcessor;

public class WebServerProperties {

    private final int port;
    private HttpRequestDispatcher requestDispatcher;
    private HttpRequestPreProcessor requestPreProcessor;

    private WebServerProperties(int port, HttpRequestDispatcher requestDispatcher, HttpRequestPreProcessor requestPreProcessor) {
        this.port = port;
        this.requestDispatcher = requestDispatcher;
        this.requestPreProcessor = requestPreProcessor;
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

    public static class Builder {

        private int port;
        private HttpRequestDispatcher requestDispatcher;
        private HttpRequestPreProcessor preProcessor;

        public WebServerProperties build(){
            return new WebServerProperties(port, requestDispatcher, preProcessor);
        }

        public Builder httpRequestDispatcher(HttpRequestDispatcher requestDispatcher){
            this.requestDispatcher = requestDispatcher;
            return this;
        }

        public Builder httpRequestPreProcessor(HttpRequestPreProcessor preProcessor){
            this.preProcessor = preProcessor;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }
    }
}
