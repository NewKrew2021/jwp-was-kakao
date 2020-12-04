package exceptions;

public class NoSuchResource extends RuntimeException {

    public NoSuchResource(String resourceName) {
        super("해당 리소스는 없습니다 : " + resourceName);
    }
}
