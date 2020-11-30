package exceptions;

public class NoSuchResource extends Exception {

    public NoSuchResource(String resourceName) {
        super("해당 리소스는 없습니다 : " + resourceName);
    }
}
