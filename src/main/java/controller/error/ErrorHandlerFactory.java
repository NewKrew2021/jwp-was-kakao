package controller.error;

import controller.handler.Handler;
import exception.utils.NoFileException;

import java.io.IOException;

public class ErrorHandlerFactory {
    public static Handler getHandler(Exception e) {
        if (e instanceof NoFileException) {
            return new NoFileExceptionHandler();
        }
        if (e instanceof IOException) {
            return new IOExceptionHandler();
        }
        return new RuntimeExceptionHandler();
    }
}
