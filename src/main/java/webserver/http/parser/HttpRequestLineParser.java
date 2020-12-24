package webserver.http.parser;

import webserver.http.HttpMethod;
import webserver.http.HttpRequestLine;
import webserver.http.ParameterBag;
import webserver.http.Protocol;

public class HttpRequestLineParser {

    public static HttpRequestLine parse(String requestLineString) {
        String[] lineTokens = requestLineString.split(" ");

        validate(lineTokens);

        String path = lineTokens[1];
        ParameterBag queryString = null;

        if(hasQueryString(path)) {
            String[] pathAndQueryString = path.split("\\?");
            path = pathAndQueryString[0];
            queryString = ParameterBagParser.parse(pathAndQueryString[1]);
        }

        return new HttpRequestLine(HttpMethod.valueOf(lineTokens[0]), path, queryString, new Protocol(lineTokens[2]));
    }

    private static boolean hasQueryString(String lineToken) {
        return lineToken.contains("?");
    }

    private static void validate(String[] values) {
        if(values.length != 3) {
            throw new IllegalArgumentException();
        }
    }
}