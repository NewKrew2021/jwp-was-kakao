package exception;

public class MapperNotFoundException extends RuntimeException {

    public <T> MapperNotFoundException(Class<T> type) {
        super(String.format("%s 클래스의 mapper가 존재하지 않습니다", type.getSimpleName()));
    }
}
