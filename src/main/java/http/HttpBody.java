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
    private HttpParameters httpParameters;

    public HttpBody(BufferedReader bufferedReader, String bodyLength, HttpParameters httpParameters ){
        this.httpParameters = httpParameters;

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
        httpParameters.parseParameter(body);
    }
}
