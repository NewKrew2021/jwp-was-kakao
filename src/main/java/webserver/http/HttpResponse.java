package webserver.http;

import annotation.web.ResponseStatus;
import com.google.common.collect.Maps;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponse {
    private final Map<String, String> headers = Maps.newHashMap();
    private final DataOutputStream dos;

    public HttpResponse(OutputStream os) {
        dos = new DataOutputStream(os);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String templatePath) {
        makeResponse(ResponseStatus.OK);
        processHeadersAndSendWithBody(new Body(templatePath));
    }

    public void forwardBody(Body body) {
        makeResponse(ResponseStatus.OK);
        processHeadersAndSendWithBody(body);
    }

    public void sendRedirect(String redirectPath) {
        makeResponse(ResponseStatus.FOUND);
        addHeader("Location", redirectPath);
        processHeadersAndSend();
    }

    public void sendStatus(ResponseStatus status) {
        makeResponse(status);
        processHeadersAndSend();
    }

    private void makeResponse(ResponseStatus responseStatus) {
        writeToOutputStream(String.format("HTTP/1.1 %d %s\r\n",
                responseStatus.getStatusCode(),
                responseStatus.getMessage()).getBytes(StandardCharsets.UTF_8));
    }

    private void processHeadersAndSend() {
        writeToOutputStream((processHeaders() + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        sendOutputStream();
    }

    private void processHeadersAndSendWithBody(Body body) {
        if (body == null) {
            throw new IllegalArgumentException("Body cannot be null");
        }

        addBodyHeaders(body);

        writeToOutputStream((processHeaders() + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        writeToOutputStream(body.getBody());

        sendOutputStream();
    }

    private void addBodyHeaders(Body body) {
        addHeader("Content-Length", Integer.toString(body.getContentLength()));
        addHeader("Content-Type", body.getContentType());
    }

    private String processHeaders() {
        return headers.keySet()
                .stream()
                .map(key -> String.format("%s: %s", key, headers.get(key)))
                .collect(Collectors.joining("\r\n"));
    }

    private void writeToOutputStream(byte[] content) {
        try {
            dos.write(content);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage() + "\nCould not write to Output Stream");
        }
    }

    private void sendOutputStream() {
        try {
            dos.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage() + "\nCould not flush data to Output Stream");
        }
    }
}
