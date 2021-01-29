package webserver;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Request {

    private final String method;
    private final Map<String, String> parameters = new HashMap<>();
    private final String version;
    private final Map<String, String> headers = new HashMap<>();
    private String path;
    private String body = "";

    public Request(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] split = line.split(" ");
        method = split[0];
        path = split[1];
        if (path.contains("?")) {
            String[] pathWithParameter = path.split("\\?");
            path = pathWithParameter[0];
            StringTokenizer stk = new StringTokenizer(pathWithParameter[1], "=&");
            while (stk.hasMoreTokens()) {
                parameters.put(stk.nextToken(), stk.nextToken());
            }
        }
        version = split[2];

        while ((line = br.readLine()) != null) {
            if ("".equals(line)) {
                break;
            }
            String[] header = line.split(": ");
            headers.put(header[0], header[1]);
        }

        if (headers.containsKey("Content-Length")) {
            body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getBody() {
        return body;
    }
}
