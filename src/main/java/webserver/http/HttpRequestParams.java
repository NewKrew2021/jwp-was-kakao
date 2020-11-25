package webserver.http;

import java.util.List;

public class HttpRequestParams {

    private static QueryParser queryParser = new QueryParser();

    public static List<HttpRequestParam> convertFrom(String queryString){
        return queryParser.parse(queryString);
    }

}
