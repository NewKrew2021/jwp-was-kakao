package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final HttpMethod method;
    private final String path;
    private final HttpHeaders headers;
    private final HttpParameters parameters;

    public HttpRequest(BufferedReader br) throws IOException {
        headers = new HttpHeaders();
        parameters = new HttpParameters();
        String[] request = br.readLine().split(" ");
        String[] url = request[1].split("\\?");
        method = HttpMethod.from(request[0]);
        path = url[0];

        makeHeaders(br);
        makeParameters(br, url);
    }

    private void makeParameters(BufferedReader br, String[] url) throws IOException {
        if (url.length > 1) {
            addParamsFromArgumentText(url[1]);
        }
        if (hasBody()) {
            int contentLength = Integer.parseInt(headers.getValue("Content-Length"));
            String body = utils.IOUtils.readData(br, contentLength);
            addParamsFromArgumentText(body);
        }
    }

    private void makeHeaders(BufferedReader br) throws IOException {


        String line;
        while ((line = br.readLine()) != null && !line.equals("")) {
            String[] buf = line.split(": ");
            headers.addHeader(buf[0], buf[1]);
        }
    }

    private boolean hasBody() {
        if (HttpMethod.GET.equals(method)) {
            return false;
        }
        if (headers.getValue("Content-Length") == null) {
            return false;
        }
        if (Integer.parseInt(headers.getValue("Content-Length")) == 0) {
            return false;
        }
        return true;
    }

    public void addParamsFromArgumentText(String argumentText) throws java.io.UnsupportedEncodingException {
        String[] arguments = argumentText.split("&");
        for (String argument : arguments) {
            String[] parameter = argument.split("=");
            parameters.addParameter(parameter[0], java.net.URLDecoder.decode(parameter[1], "UTF-8"));
        }
    }

    public String getHeaderValue(String headerName) {
        return headers.getValue(headerName);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String ParameterName) {
        return parameters.getValue(ParameterName);
    }
}
