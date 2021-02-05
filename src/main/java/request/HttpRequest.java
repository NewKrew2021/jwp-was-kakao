package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import utils.ParseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static common.HttpHeaders.CONTENT_LENGTH;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final String REQUEST_FIRST_LINE_REGEX = " ";
    private final String DEFAULT_LINE = "";
    private final int METHOD_INDEX = 0;
    private final int URL_INDEX = 1;
    private final int VERSION_INDEX = 2;

    private HttpMethod method;
    private String url;
    private String version;
    private String path;

    private Map<String, String> headers;
    private Map<String, String> bodies;

    public HttpRequest() {
    }

    private HttpRequest(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        getMethodAndUrl(reader);
        headers = makeHeaders(reader);
        bodies = makeBodies(reader);
    }

    public static HttpRequest of(InputStream in) throws IOException {
        return new HttpRequest(in);
    }

    private Map<String, String> makeHeaders(BufferedReader reader) {
        List<String> lines = readLines(reader);
        return lines.stream()
                .skip(1)
                .limit(lines.size() - 2)
                .map(header -> ParseUtils.parseHeaderPair(header))
                .collect(Collectors.toMap(HeaderPair::getKey,HeaderPair::getKey));
    }

    private void getMethodAndUrl(BufferedReader reader) {
        String[] lines = readLine(reader).split(REQUEST_FIRST_LINE_REGEX);
        method = HttpMethod.of(lines[METHOD_INDEX]);
        url = lines[URL_INDEX];
        version = lines[VERSION_INDEX];
        path = ParseUtils.getUrlPath(url);
    }

    private List<String> readLines(BufferedReader reader) {
        List<String> lines = new ArrayList<>();
        lines.add(readLine(reader));

        while(lines.get(lines.size() - 1) != null &&
                !lines.get(lines.size() - 1).isEmpty()){
            lines.add(readLine(reader));
        }

        return lines;
    }

    private String readLine(BufferedReader reader) {
        try {
            String line = reader.readLine();
            logger.debug(line);
            return line;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return DEFAULT_LINE;
        }
    }

    private Map<String, String> makeBodies(BufferedReader reader) throws IOException {
        Map<String, String> bodies = new HashMap<>();

        if (ParseUtils.containRequestUrlRegex(url)) {
            bodies.putAll(ParseUtils.getParameters(ParseUtils.getParameterPairs(url)));
        }

        if(method.equals(HttpMethod.POST)){
            String body = IOUtils.readData(reader, Integer.parseInt(headers.get(CONTENT_LENGTH.getHeader())));
            bodies.putAll(ParseUtils.getParameters(body));
        }

        return bodies;
    }


    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getBodies() {
        return bodies;
    }
}
