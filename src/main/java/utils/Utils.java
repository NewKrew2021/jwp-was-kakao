package utils;

public class Utils {

    public static byte[] defaultIfNull(byte[] b) {
        return b == null ? new byte[0] : b;
    }

}
