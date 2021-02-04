package controller.error;

import controller.handler.ErrorHandler;
import exception.utils.NoFileException;

import java.io.IOException;

public class ErrorHandlerFactory {
    public static ErrorHandler getHandler(Exception e) {
        if (e instanceof NoFileException) {
            return new NoFileExceptionHandler();
        }
        if (e instanceof IOException) {
            return new IOExceptionHandler();
        }
        return new RuntimeExceptionHandler();
    }
}
