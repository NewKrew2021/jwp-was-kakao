package web;

import exception.MapperNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class BodyMapper {

    private static final TypeSafeMap mappers = new TypeSafeMap();

    static {
        mappers.put(String.class, String::new);
    }

    public static <T> T read(byte[] content, Class<T> type) {
        Mapper<T> mapper = mappers.get(type);
        if (mapper == null) {
            throw new MapperNotFoundException(type);
        }
        return mapper.map(content);
    }

    private static class TypeSafeMap {
        private final Map<Class<?>, Mapper<?>> mappers = new HashMap<>();

        private <T> void put(Class<T> key, Mapper<T> value) {
            mappers.put(key, value);
        }

        private <T> Mapper<T> get(Class<T> key) {
            return (Mapper<T>) mappers.get(key);
        }
    }

    interface Mapper<T> {
        T map(byte[] content);
    }
}
