package webserver.http;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExceptionHandlerResolver {

    private Map<Class,ExceptionHandler> handlers;
    private ExceptionHandler defaultHandler;

    public ExceptionHandlerResolver(){
        this.handlers = new HashMap<>();
        this.defaultHandler = new ServerErrorHandler();
    }

    public ExceptionHandlerResolver(ExceptionHandler defaultHandler){
        this.handlers = new HashMap<>();
        this.defaultHandler = defaultHandler;
    }

    public void addHandler(Class exceptionClass, ExceptionHandler handler){
        handlers.put(exceptionClass, handler);
    }

    public ExceptionHandler resolve(Object exceptionInstance){
        Optional found = handlers.keySet()
                .stream()
                .filter(clazz -> clazz.isInstance(exceptionInstance))
                .findFirst();

        if( found.isPresent() ){
            return handlers.get(found.get());
        }
        return defaultHandler;
    }

    private class ServerErrorHandler implements ExceptionHandler {
        Logger logger = LoggerFactory.getLogger(ServerErrorHandler.class);
        @Override
        public void handle(Exception e, HttpRequest httpRequest, HttpResponse httpResponse) {
            logger.debug(e.getMessage(), e);
            httpResponse.setStatus(HttpStatus.x500_InternalServerError);
            httpResponse.setBody(e.getMessage().getBytes(Charsets.UTF_8));
            httpResponse.send();
        }
    }
}
