package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private final String method;
    private final String path;
    private final String version;
    private final Map<String, String> headers = new HashMap<>();

    public Request(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] split = line.split(" ");
        method = split[0];
        path = split[1];
        version = split[2];

        while ((line = br.readLine()) != null) {
            if ("".equals(line)) {
                break;
            }
            String[] header = line.split(": ");
            headers.put(header[0], header[1]);
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
}
