package domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static utils.FileIoUtils.loadFileFromClasspath;

public class Response {
    private final HttpVersion httpVersion;
    private final StatusCode statusCode;
    private final ResponseHeaders headers;
    private final ResponseBody body;
    private final ContentType contentType;

    public Response(HttpVersion httpVersion,
                    StatusCode statusCode,
                    ResponseHeaders headers,
                    ResponseBody body,
                    ContentType contentType) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
        this.contentType = contentType;
    }

    public static Response ofDefaultFile(ResponseBody body, ContentType contentType) {
        ResponseHeaders headers = new ResponseHeaders();
        headers.addHeader("Content-Type", contentType.getType() + ";charset=utf-8");
        headers.addHeader("Content-Length", body.getByteSize() + "");
        return new Response(
                HttpVersion.HTTP1_1,
                StatusCode.OK,
                headers,
                body,
                contentType
        );
    }

    public static Response ofRedirect(String redirectUrl) {
        ResponseHeaders headers = new ResponseHeaders();
        headers.addHeader("Content-Type", ContentType.HTML.getType() + ";charset=utf-8");
        headers.addHeader("Location", redirectUrl);
        return new Response(
                HttpVersion.HTTP1_1,
                StatusCode.FOUND,
                headers,
                ResponseBody.ofEmptyBody(),
                ContentType.HTML
        );
    }

    public static Response ofDynamicHtml(ResponseBody responseBody) {
        ResponseHeaders headers = new ResponseHeaders();
        headers.addHeader("Content-Type", ContentType.HTML.getType() + ";charset=utf-8");
        headers.addHeader("Content-Length", responseBody.getByteSize() + "");
        return new Response(
                HttpVersion.HTTP1_1,
                StatusCode.OK,
                headers,
                responseBody,
                ContentType.HTML
        );

    }

    public static Response ofFileFromUrlPath(String urlPath) throws IOException, URISyntaxException {
        if (urlPath.contains(".html")) {
            return Response.ofDefaultFile(
                    new ResponseBody(loadFileFromClasspath("./templates" + urlPath)),
                    ContentType.HTML
            );
        }
        if (urlPath.contains(".ico")) {
            return Response.ofDefaultFile(
                    new ResponseBody(loadFileFromClasspath("./templates" + urlPath)),
                    ContentType.ICO);
        }
        if (urlPath.matches("(.*)(.js|.css|.eot|.svg|.ttf|.woff|.woff2|.png)")) {
            List<String> split = Arrays.asList(urlPath.split("\\."));
            String extension = split.get(split.size() - 1);
            return Response.ofDefaultFile(
                    new ResponseBody(loadFileFromClasspath("./static" + urlPath)),
                    ContentType.valueOf(extension.toUpperCase()));
        }
        return Response.ofDefaultFile(new ResponseBody("No Page!"),
                ContentType.HTML);
    }

    public void addHeader(String key, String value) {
        headers.addHeader(key, value);
    }

    public byte[] toBytes() {
        return this.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return httpVersion + " " + statusCode + "\r\n"
                + headers + "\r\n\r\n"
                + body + "\r\n\r\n";
    }
}
