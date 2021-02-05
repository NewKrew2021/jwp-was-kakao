package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private static final String URL_ENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private String method;
    private String path;
    private Map<String, String> headers;
    private Map<String, String> parameters;

    public HttpRequest(BufferedReader br) throws IOException {
        String[] request = br.readLine().split(" ");
        String[] url = request[1].split("\\?");
        method = request[0];
        path = url[0];

        makeHeaders(br);
        makeParameters(br, url);
        makeSession();
    }

    private void makeSession() {
        Cookie cookie = new Cookie(this);
        Optional<String> sessionId = cookie.getSession();

        if(sessionId.isPresent() && !SessionHandler.getSession(sessionId)){
            SessionHandler.addSession(sessionId.get());
        }
    }

    private void makeParameters(BufferedReader br, String[] url) throws IOException {
        parameters = new HashMap<>();

        if (url.length > 1){
            parseArgument(url[1]);
        }
        if (method.equals("POST") && getHeader("Content-Type").equals(URL_ENCODED_CONTENT_TYPE)) {
            int contentLength = Integer.parseInt(getHeader("Content-Length"));
            String body = utils.IOUtils.readData(br, contentLength);
            parseArgument(body);
        }
    }

    private void makeHeaders(BufferedReader br) throws IOException {
        headers = new HashMap<>();

        String line;
        while((line = br.readLine()) != null && !line.equals("")){
            String[] buf = line.replaceAll(" ", "").split(":");
            headers.put(buf[0], buf[1]);
        }
    }

    protected void parseArgument(String argumentText) throws java.io.UnsupportedEncodingException {
        String[] arguments = argumentText.split("&");
        for (String argument : arguments) {
            String[] parameter = argument.split("=");
            parameters.put(parameter[0], java.net.URLDecoder.decode(parameter[1], "UTF-8"));
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String header) {
        return this.headers.get(header);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
