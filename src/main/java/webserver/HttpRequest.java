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
import java.util.function.Function;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private static final String DEFAULT_CHARSET = "utf-8";

    private static final String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_PARAM_CHARSET = "charset";

    private String method;
    private String target;
    private String httpVersion;
    private HttpHeaders httpHeaders;
    private char[] body;

    private Cookie cookie;
    private ParamMap requestParams = new ParamMap();

    public HttpRequest(String firstLine, BufferedReader br) throws HttpException {
        parse(firstLine, br);
    }

    public void parse(String requestLine, BufferedReader br) throws HttpException {
        try {
            readRequestLine(requestLine);

            readHeaders(br);

            // TODO check host header

            readBody(br);

            readCookieFromHeaders();
            readRequestParamsFromBody();
        } catch (Exception e) {
            logger.error("fail to parse http req", e);

            throw HttpException.badRequest();
        }
    }

    private boolean readRequestLine(String requestLine) throws HttpException {
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
        httpHeaders = new HttpHeaders();

        String header;
        while (!(header = br.readLine()).equals("")) {
            String[] headerKv = header.split(":", 2);

            String k = headerKv[0];
            String v = headerKv[1].trim();

            httpHeaders.addHeader(k, v);

            logger.debug("<< {} : {}", k, v);
        }
    }

    private void readBody(BufferedReader br) throws HttpException, IOException {
        if (getFirstHeaderValue(HttpHeader.CONTENT_LENGTH) != null) {
            int clen = getFirstHeaderValueInt(HttpHeader.CONTENT_LENGTH);
            if (clen > Integer.MAX_VALUE) {
                throw new HttpException(HttpStatus.PAYLOAD_TOO_LARGE);
            }

            body = new char[clen];
            br.read(body);

            logger.debug("<< (body) {} bytes", clen);
        }
    }

    private void readRequestParamsFromBody() throws HttpException {
        String mediaType = getFirstHeaderValue(HttpHeader.CONTENT_TYPE);

        if (mediaType != null && mediaType.startsWith(CONTENT_TYPE_FORM_URL_ENCODED)) {
            // check charset first
            int paramIndex = mediaType.indexOf(";");
            if (paramIndex >= 0) {
                ParamMap mediaTypeParams = new ParamMap(mediaType.substring(paramIndex), ";", "=", Function.identity());

                String charset = mediaTypeParams.getOrDefault(CONTENT_TYPE_PARAM_CHARSET, DEFAULT_CHARSET);

                if (charset != DEFAULT_CHARSET) {
                    throw new HttpException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                }
            }

            requestParams = new ParamMap(new String(body), "&", "=", v -> Utils.decodeUrl(v));
        }
    }

    private void readCookieFromHeaders() {
        String c = getFirstHeaderValue(HttpHeader.COOKIE);
        cookie = new Cookie(c);
    }

    public String getMethod() {
        return method;
    }

    public String getTarget() {
        return target;
    }

    public String getFirstHeaderValue(String key) {
        return httpHeaders.getFirstHeaderValue(key);
    }

    public int getFirstHeaderValueInt(String key) {
        return Integer.parseInt(getFirstHeaderValue(key));
    }

    public String getRequestParam(String key) {
        return requestParams.get(key);
    }

    public Cookie getCookie() {
        return cookie;
    }
}
