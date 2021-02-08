package controller.error;

import controller.handler.ErrorHandler;
import exception.http.IllegalRequestException;
import exception.user.UserInputException;
import exception.utils.NoFileException;

public class ErrorHandlerFactory {
    public static ErrorHandler getHandler(Exception e) {
        if (e instanceof NoFileException) {
            return new NotFoundHandler();
        }
        if (e instanceof IllegalRequestException || e instanceof UserInputException) {
            return new BadRequestHandler();
        }
        return new InternalServerErrorHandler();
    }
}
