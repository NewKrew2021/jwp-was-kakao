package exception;

@FunctionalInterface
public interface ExceptionFunction {
    void sendResponse() throws HttpResponseOutputException;
}
