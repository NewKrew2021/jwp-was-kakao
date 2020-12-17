package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;
import webserver.constant.HttpHeader;
import webserver.constant.HttpMessage;
import webserver.constant.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private int statusCode;
    private String reasonPhrase;
    private Map<String, String> loweredKeyHeaders;
    private byte[] body;

    public HttpResponse(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.loweredKeyHeaders = new HashMap<>();
        this.body = new byte[0];
    }

    public HttpResponse(HttpStatus httpStatus) {
        this(httpStatus.getStatusCode(), httpStatus.getReasonPhrase());
    }

    public HttpResponse() {
        this(HttpStatus.OK);
    }

    public HttpResponse putHeader(String k, String v) {
        loweredKeyHeaders.put(k.toLowerCase(), v);
        return this;
    }

    public HttpResponse setBody(byte[] body) {
        return setBody(body, true);
    }

    public HttpResponse setBody(byte[] body, boolean setContentLength) {
        this.body = body;

        if (setContentLength) {
            putHeader(HttpHeader.CONTENT_LENGTH, Integer.toString(body.length));
        }

        return this;
    }

    public HttpResponse setFileAsBody(String resourcePath) throws IOException, URISyntaxException {
        return setFileAsBody(resourcePath, true, true);
    }

    public HttpResponse setFileAsBody(String resourcePath, boolean setContentLength, boolean guessContentType) throws IOException, URISyntaxException {
        byte[] bytes = FileUtils.loadFileFromClasspath(resourcePath);

        if (guessContentType) {
            putHeader(HttpHeader.CONTENT_TYPE, FileUtils.guessContentType(resourcePath));
        }

        return setBody(bytes, setContentLength);
    }

    public String getHeaderValue(String key) {
        return loweredKeyHeaders.get(key.toLowerCase());
    }

    // TODO extract to buildPayloadString?
    @Override
    public String toString() {
        String payload = "";

        String respLine = String.format("%s %d %s", HttpMessage.DEFAULT_HTTP_VERSION, statusCode, reasonPhrase);
        logger.debug(">> {}", respLine);

        payload += respLine + HttpMessage.CRLF;

        for (Map.Entry<String, String> headerEntry : loweredKeyHeaders.entrySet()) {
            String header = String.format("%s: %s", headerEntry.getKey(), headerEntry.getValue());
            logger.debug(">> {}", header);

            payload += header + HttpMessage.CRLF;
        }

        payload += HttpMessage.CRLF;

        if (body.length > 0) {
            payload += new String(body);

            logger.debug(">> (body) {} bytes", body.length);
        }

        return payload;
    }

}
