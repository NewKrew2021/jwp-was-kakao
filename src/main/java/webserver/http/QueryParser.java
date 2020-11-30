package webserver.http;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryParser {

    public List<HttpRequestParam> parse(String query) {
        if (StringUtils.isEmpty(query)) return new ArrayList();

        String[] params = query.split("&");
        return Arrays.stream(params)
                .map(HttpRequestParam::new)
                .collect(Collectors.toList());
    }

}
