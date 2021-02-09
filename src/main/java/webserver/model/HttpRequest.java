package webserver.model;

import utils.IOUtils;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class HttpRequest {


    private HttpMethod method;
    private String path;
    private HttpHeader header;
    private Parameter parameter;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        setMethodAndPath(br);
        setHeader(br);
        setParameter(br);
    }

    private void setMethodAndPath(BufferedReader br) throws IOException {
        String line = IOUtils.readLine(br);
        String[] parsed = line.split(StringUtils.WHITE_SPACE);
        method = HttpMethod.of(parsed[0]);
        path = parsed[1];
    }

    private void setParameter(BufferedReader br) throws IOException {
        if (getMethod().equals(HttpMethod.GET) && path.contains("?")) {
            String[] parsed = path.split("\\?");
            path = parsed[0];
            parameter = new Parameter(parsed[1]);
        }
        if (getMethod().equals(HttpMethod.POST)) {
            int bodySize = Integer.parseInt(header.getHeader(HttpHeader.CONTENT_LENGTH));
            parameter = new Parameter(IOUtils.readData(br, bodySize));
        }
    }

    private void setHeader(BufferedReader br) throws IOException {
        header = new HttpHeader(IOUtils.readUntilDelimiter(br, HttpHeader.HEADER_END_WITH));
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String parameter) {
        return this.parameter.get(parameter);
    }

    public String getCookie(String key) {
        return header.getCookie(key);
    }

    public boolean isEmpty() {
        return path == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HttpRequest\n").append("Method = ").append(method).append("\nPath = ").append(path).append("\n");
        return sb.toString();
    }
}
