package http;

import model.PagePath;
import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    public final static String HTTP_VERSION = "HTTP/1.1";
    public final static String LOCATION = "Location";
    public final static String SET_COOKIE = "Set-Cookie";
    public final static String PATH = "; Path=/";
    public final static String CONTENT_TYPE = "Content-Type";
    public final static String CONTENT_LENGTH = "Content-Length";
    public final static String ACCEPT = "Accept";
    public final static String HOST = "Host";
    public final static String ENCODING = "text/html;charset=utf-8";
    public final static String HTTP = "http://";

    private Map<String, String> httpHeader = new HashMap<>();
    private HttpParameters httpParameters;
    private HttpMethod httpMethod;
    private PagePath pagePath;
    private String version;

    public HttpHeader(BufferedReader bufferedReader, HttpParameters httpParameters) throws IOException {
        this.httpParameters = httpParameters;

        String line = bufferedReader.readLine();
        parseFirstLine(line);
        parseHeader(bufferedReader);
    }

    private void parseHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while ( line != null && !line.equals("")) {
            String[] currentLine = line.split(": ");
            httpHeader.put(currentLine[0], URLDecoder.decode(currentLine[1], StandardCharsets.UTF_8));
            line = bufferedReader.readLine();
        }
    }

    private void parseFirstLine(String line) throws UnsupportedEncodingException {
        String[] currentLine = line.split(" ");
        httpMethod = HttpMethod.resolve(currentLine[0]);

        String[] pathAndParameter = currentLine[1].split("\\?");
        pagePath = new PagePath(pathAndParameter[0]);

        if( pathAndParameter.length > 1 ) {
            httpParameters.parseParameter(pathAndParameter[1]);
        }

        version = currentLine[2];
    }

    public String getHeaderByKey(String key) {
        return httpHeader.getOrDefault(key, "");
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public PagePath getPath() {
        return pagePath;
    }

    public String getVersion() {
        return version;
    }

}
