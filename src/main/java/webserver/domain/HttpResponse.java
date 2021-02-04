package webserver.domain;

import annotation.web.ResponseMethod;
import com.google.common.collect.Maps;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponse {
    private static final String LOCATION = "Location";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String KEY_HEADER = "%s: %s";
    private static final String NEXT_LINE = "\r\n";
    private static final String HTTP_1_1_STATUS_MESSAGE = "HTTP/1.1 %d %s";
    private Map<String, String> headers = Maps.newHashMap();
    private DataOutputStream dos;

    public HttpResponse(OutputStream os) {
        dos = new DataOutputStream(os);
    }

    public void forward(String path) {
        makeResponse(ResponseMethod.OK);
        addHeadersAndSend(new Body(path));
    }

    public void forwardBody(Body body) {
        makeResponse(ResponseMethod.OK);
        addHeadersAndSend(body);
    }

    public void sendRedirect(String path) {
        makeResponse(ResponseMethod.FOUND);
        addHeader(LOCATION, path);
        addHeadersAndSend(null);
    }

    private void addHeadersAndSend(Body body) {
        if (body != null && body.isNotEmpty()) {
            addHeader(CONTENT_LENGTH, Integer.toString(body.getContentLength()));
            addHeader(CONTENT_TYPE, body.getContentType());
        }

        String resultHeaders = headers.keySet()
                .stream()
                .map(key -> String.format(KEY_HEADER, key, headers.get(key)))
                .collect(Collectors.joining(NEXT_LINE));

        try {
            dos.writeBytes(resultHeaders + NEXT_LINE);
            dos.writeBytes(NEXT_LINE);
            if (body != null) {
                dos.write(body.getBody());
            }
            dos.flush();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error when make header");
        }
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    private void makeResponse(ResponseMethod responseMethod) {
        try {
            dos.writeBytes(String.format(HTTP_1_1_STATUS_MESSAGE + NEXT_LINE,
                    responseMethod.getStatusCode(), responseMethod.getMessage()));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while make response");
        }
    }
}
