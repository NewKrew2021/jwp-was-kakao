package http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpBody {

    private String body;
    private Map<String, String> bodyParameters = new HashMap<>();

    public HttpBody(BufferedReader bufferedReader, String bodyLength ){
        try {
            int length = Integer.parseInt(bodyLength);
            parseBody(bufferedReader, length);
        } catch (Exception e) {

        }
    }

    private void parseBody(BufferedReader bufferedReader, int length) throws IOException {
        if( length == 0 ) {
            return;
        }
        body = IOUtils.readData(bufferedReader, length);
        parseParameter();
    }

    private void parseParameter() throws UnsupportedEncodingException {
        String[] parameters = body.split("&|=");
        for (int i = 0; i < parameters.length; i = i + 2) {
            this.bodyParameters.put(parameters[i], URLDecoder.decode( parameters[i + 1], "UTF-8" ));
        }
    }

    public Map<String, String> getBodyParameters() {
        return bodyParameters;
    }
}
