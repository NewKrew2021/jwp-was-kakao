package webserver.domain;

import annotation.web.ResponseMethod;
import com.google.common.collect.Maps;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponse {
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
        addHeader("Location", path);
        addHeadersAndSend(null);
    }

    private void addHeadersAndSend(Body body) {
        if (body != null && body.isNotEmpty()) {
            addHeader("Content-Length", Integer.toString(body.getContentLength()));
            addHeader("Content-Type", body.getContentType());
        }

        String resultHeaders = headers.keySet()
                .stream()
                .map(key -> String.format("%s: %s", key, headers.get(key)))
                .collect(Collectors.joining("\r\n"));

        try {
            dos.writeBytes(resultHeaders + "\r\n");
            dos.writeBytes("\r\n");
            if (body != null) {
                dos.write(body.getBody());
            }
            dos.flush();
        } catch (IOException e) {
            throw new IllegalArgumentException("asd");
        }
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    private void makeResponse(ResponseMethod responseMethod) {
        try {
            dos.writeBytes(String.format("HTTP/1.1 %d %s\r\n",
                    responseMethod.getStatusCode(), responseMethod.getMessage()));
        } catch (IOException e) {
            throw new IllegalArgumentException("asdfasdf");
        }
    }
}
