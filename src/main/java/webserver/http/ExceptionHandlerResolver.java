package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExceptionHandlerResolver {

    private Map<Class,ExceptionHandler> handlers;
    private ExceptionHandler defaultHandler;

    public ExceptionHandlerResolver(){
        this(new HashMap<>(), new ServerErrorHandler());
    }

    public ExceptionHandlerResolver(Map<Class, ExceptionHandler> handlers) {
        this(handlers, new ServerErrorHandler());
    }

    public ExceptionHandlerResolver(Map<Class, ExceptionHandler> handlers, ExceptionHandler defaultHandler){
        this.handlers = handlers;
        this.defaultHandler = defaultHandler;
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

}
