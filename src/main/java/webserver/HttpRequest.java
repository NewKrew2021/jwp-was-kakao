package webserver;

import webserver.constant.HttpMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final String HEADER_HOST = "Host";

    private String method;
    private String target;
    private String httpVersion;
    private Map<String, String> headerLoweredKeyValue;
    private char[] body;

    public HttpRequest(InputStream in) throws IOException, HttpException {
        parse(in);
    }

    public void parse(InputStream in) throws IOException, HttpException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        final String requestStatusLine = br.readLine();

        // TODO throw 400 if fail

        String[] split = requestStatusLine.split(HttpMessage.SP);

        method = split[0];
        target = split[1];
        httpVersion = split[2];

        if (!HttpMessage.SUPPORTED_HTTP_VERSION_SET.contains(httpVersion)) {
            throw HttpException.notImplemented(httpVersion);
        }

        headerLoweredKeyValue = new HashMap<>();

        String header;
        while (!(header = br.readLine()).equals("")) {
            String[] headerKv = header.split(HttpMessage.SP);

            String key = headerKv[0];
            String value = headerKv[1];

            headerLoweredKeyValue.put(key.toLowerCase(), value);
        }

        // TODO check host header

        // TODO read body
//        if (getHeaderValue(HttpHeader.CONTENT_LENGTH) == null) {
//           throw HttpException.lengthRequired();
//        }
//
//        int clen = getHeaderValueInt(HttpHeader.CONTENT_LENGTH);
//        if (clen > Integer.MAX_VALUE) {
//            throw HttpException.payloadTooLarge();
//        }
//
//        char[] body = new char[clen];
//        br.read(body);

    }

    public String getHeaderValue(String key) {
        return headerLoweredKeyValue.get(key.toLowerCase());
    }

    public int getHeaderValueInt(String key) {
        return Integer.parseInt(getHeaderValue(key));
    }

    public String getMethod() {
        return method;
    }

    public String getTarget() {
        return target;
    }

}
