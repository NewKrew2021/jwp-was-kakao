package utils;

public class Parser {

    public static String parseURLFromHeader(String header) {
        String[] token = header.split(" ");
        return token[1];
    }
}
