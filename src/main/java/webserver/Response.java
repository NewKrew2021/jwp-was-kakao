package webserver;

import dto.ParamValue;
import model.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private final String url;
    private final HttpStatus httpStatus;
    private final Optional<ParamValue> httpValue;
    private final byte[] body;

    private Response(String url, HttpStatus httpStatus, Optional<ParamValue> httpValue, byte[] body) {
        this.url = url;
        this.httpStatus = httpStatus;
        this.httpValue = httpValue;
        this.body = body;
    }

    public static Response of(Request request, HttpStatus httpStatus) {
        String url = request.getURL();
        return new Response(request.getURL(),
                            httpStatus,
                            Optional.empty(),
                            ResponseHandler.getBody(url));
    }

    public static Response of(Request request, HttpStatus httpStatus, String body) {
        return new Response(request.getURL(),
                            httpStatus,
                            Optional.empty(),
                            body.getBytes());
    }

    public static Response ofDirect(Request request, ParamValue httpValue) {
        String url = request.getURL();
        return new Response(url,
                            HttpStatus.HTTP_FOUND,
                            Optional.of(httpValue),
                            ResponseHandler.getBody(url));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String descHttpStatusCode() {
        return httpStatus.descHttpStatusCode();
    }

    public List<String> getAddHttpDesc() {
        List<String> addHttpDesc = new ArrayList<>();

        httpValue.ifPresent(paramValue -> paramValue.getParamMap()
                .forEach((key, value) -> addHttpDesc.add(String.format("%s: %s \r\n", key, value)))
        );

        return addHttpDesc;
    }

    public byte[] getBody() {
        return ResponseHandler.getBody(url);
    }


    public void output(DataOutputStream dos) {
        if (isCSS()) {
            responseCSSHeader(dos, body.length);
            responseBody(dos, body);
        } else {
            responseHeader(dos, body.length);
            responseBody(dos, body);
        }
    }

    public boolean isCSS() {
        return url.startsWith("/css");
    }

    public void responseCSSHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            String format = String.format("HTTP/1.1 %s \r\n", descHttpStatusCode());
            dos.writeBytes(format);

            getAddHttpDesc().forEach(value -> {
                try {
                    dos.writeBytes(value);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
