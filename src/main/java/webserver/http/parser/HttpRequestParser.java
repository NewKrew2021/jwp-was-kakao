package webserver.http.parser;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import utils.IOUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestHeader;
import webserver.http.HttpRequestLine;
import webserver.http.ParameterBag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {

    public static HttpRequest fromInputStream(InputStream in) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        HttpRequestLine requestLine = parseRequestLine(reader);
        HttpRequestHeader header = parseHeader(reader);
        ParameterBag params = parseBody(reader, header);

        return new HttpRequest(requestLine, header, params);
    }

    public static ParameterBag parseBody(BufferedReader reader, HttpRequestHeader header) throws IOException {

        String body = IOUtils.readData(reader, header.getBodySize());
        return ParameterBagParser.parse(body);

    }

    public static HttpRequestLine parseRequestLine(BufferedReader reader) throws IOException {
        String requestLineString = reader.readLine();
        return HttpRequestLineParser.parse(requestLineString);
    }

    public static HttpRequestHeader parseHeader(BufferedReader reader) throws IOException {

        Map<String, String> headers = new HashMap<>();

        String line;
        while (StringUtils.isNotBlank(line = reader.readLine())) {
            String[] keyAndValue = line.split(": ");
            headers.put(keyAndValue[0], keyAndValue[1]);
        }

        return new HttpRequestHeader(headers);
    }

}
