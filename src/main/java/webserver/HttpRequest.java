package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ParamMap;
import utils.Utils;
import webserver.constant.HttpHeader;
import webserver.constant.HttpMessage;
import webserver.constant.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private static final String DEFAULT_CHARSET = "utf-8";

    private static final String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_PARAM_CHARSET = "charset";

    private String method;
    private String target;
    private String httpVersion;
    private Map<String, String> headerLoweredKeyValue;
    private char[] body;

    private ParamMap requestParams = new ParamMap();

    public HttpRequest(String firstLine, BufferedReader br) throws IOException, HttpException {
        parse(firstLine, br);
    }

    public boolean parse(String requestLine, BufferedReader br) throws IOException, HttpException {
        // TODO throw 400 if fail

        readRequestLine(requestLine);

        readHeaders(br);

        // TODO check host header

        readBody(br);

        readRequestParamsFromBody();

        return true;
    }

    private void readRequestParamsFromBody() throws HttpException {
        String mediaType = getHeaderValue(HttpHeader.CONTENT_TYPE);

        if (mediaType != null && mediaType.startsWith(CONTENT_TYPE_FORM_URL_ENCODED)) {
            // check charset first
            int paramIndex = mediaType.indexOf(";");
            if (paramIndex >= 0) {
                ParamMap mediaTypeParams = new ParamMap(mediaType.substring(paramIndex));

                String charset = mediaTypeParams.getOrDefault(CONTENT_TYPE_PARAM_CHARSET, DEFAULT_CHARSET);

                if (charset != DEFAULT_CHARSET) {
                    throw new HttpException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                }
            }

            requestParams = new ParamMap(new String(body), "&", "=", v -> Utils.decodeUrl(v));
        }
    }

    private boolean readRequestLine(String requestLine) throws IOException, HttpException {
        logger.debug("{}", requestLine);

        String[] split = requestLine.split(HttpMessage.SP);

        method = split[0];
        target = split[1];
        httpVersion = split[2];

        if (!HttpMessage.SUPPORTED_HTTP_VERSION_SET.contains(httpVersion)) {
            throw HttpException.notImplemented(httpVersion);
        }

        logger.debug("<< {} {} {}", method, target, httpVersion);

        return true;
    }

    private void readHeaders(BufferedReader br) throws IOException {
        headerLoweredKeyValue = new HashMap<>();

        String header;
        while (!(header = br.readLine()).equals("")) {
            String[] headerKv = header.split(":", 2);

            String key = headerKv[0];
            String value = headerKv[1].trim();

            headerLoweredKeyValue.put(key.toLowerCase(), value);

            logger.debug("<< {} : {}", key, value);
        }
    }

    private void readBody(BufferedReader br) throws HttpException, IOException {
        if (getHeaderValue(HttpHeader.CONTENT_LENGTH) != null) {
            int clen = getHeaderValueInt(HttpHeader.CONTENT_LENGTH);
            if (clen > Integer.MAX_VALUE) {
                throw new HttpException(HttpStatus.PAYLOAD_TOO_LARGE);
            }

            body = new char[clen];
            br.read(body);

            logger.debug("<< (body) {} bytes", clen);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getTarget() {
        return target;
    }

    public String getHeaderValue(String key) {
        return headerLoweredKeyValue.get(key.toLowerCase());
    }

    public int getHeaderValueInt(String key) {
        return Integer.parseInt(getHeaderValue(key));
    }

    public String getRequestParam(String key) {
        return requestParams.get(key);
    }

}
