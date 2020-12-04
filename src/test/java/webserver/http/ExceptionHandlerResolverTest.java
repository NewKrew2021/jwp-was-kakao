package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerResolverTest {

    @DisplayName("exception 을 넘기면 exceptionHandler 를 돌려준다")
    @Test
    void resolve(){
        // IOException -> FileNotFoundException
        ExceptionHandlerResolver resolver = new ExceptionHandlerResolver(new DefaultExceptionHandler());
        resolver.addHandler(IOException.class, new IOExceptionHandler());

        assertThat(resolver.resolve(new FileNotFoundException()) instanceof IOExceptionHandler).isTrue();
        assertThat(resolver.resolve(new IOException()) instanceof IOExceptionHandler).isTrue();
    }

    @DisplayName("exception 으로 exceptionHandler 를 찾지 못하면 defaultHandler 를 돌려준다")
    @Test
    void defaultHandler(){
        ExceptionHandlerResolver resolver = new ExceptionHandlerResolver(new DefaultExceptionHandler());
        resolver.addHandler(IOException.class, new IOExceptionHandler());

        assertThat(resolver.resolve(new RuntimeException()) instanceof DefaultExceptionHandler).isTrue();
    }


    class DefaultExceptionHandler implements ExceptionHandler{
        @Override
        public void handle(Exception e, HttpRequest httpRequest, HttpResponse httpResponse) {
            // default
        }
    }

    class IOExceptionHandler implements ExceptionHandler{
        @Override
        public void handle(Exception e, HttpRequest httpRequest, HttpResponse httpResponse) { }
    }
}