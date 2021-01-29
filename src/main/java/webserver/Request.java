package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.*;
import java.util.HashMap;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    HashMap<String, String> header = new HashMap<>();
    private String method;
    private String uri;
    private String body;

    private Request(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line = br.readLine();
            header.put("Request-Line", line);
            logger.debug("request line : {}", line);
            while (!"".equals(line) && line != null) {
                line = br.readLine();
                mapHeader(line);
                logger.debug("header : {}", line);
            }

            String[] requestLine = header.get("Request-Line").split(" ");
            this.method = requestLine[0];
            this.uri = requestLine[1];
            if (header.containsKey("Content-Length")) {
                this.body = IOUtils.readData(br, Integer.parseInt(header.get("Content-Length")));
                logger.debug("body : {} ", body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mapHeader(String line) {
        if (!line.equals("")) {
            String[] splitLine = line.split(":");
            header.put(splitLine[0], line.substring(splitLine[0].length() + 1).trim());
        }
    }

    private Request(String request) {
        String[] requestLine = request.split(" ");
        this.method = requestLine[0];
        this.uri = requestLine[1];
    }


    public static Request of(InputStream in) {
        return new Request(in);
    }

    public static Request of(String request) {
        return new Request(request);
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getUserRequestParam() {
        return uri.split("\\?")[1];
    }

    public String getBody() {
        return body;
    }
}
