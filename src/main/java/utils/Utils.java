package utils;

public class Utils {

    // FIXME ???
    public static <T> T defaultIfNull(T v) {
        Class<?> cls = v.getClass();
        
        if (v != null) {
            return v;
        }

        try {
            return (T)cls.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getCause());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static byte[] defaultIfNull(byte[] b) {
        return b == null ? new byte[0] : b;
    }

}
