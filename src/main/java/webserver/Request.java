package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PathUtils;

import java.io.*;

public class Request {
    private String method;
    private String uri;
    private String body;

    private Request(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();

            String[] requestLine = line.split(" ");
            this.method = requestLine[0];
            this.uri = requestLine[1];

            logger.debug("request line : {}", line);
            while (!"".equals(line) && line != null) {
                line = br.readLine();
                logger.debug("header : {}", line);
            }

        } catch (IOException e) {
            e.printStackTrace();
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
}
