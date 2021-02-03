package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private Map<String, String> httpHeader = new HashMap<>();

    public HttpHeader(BufferedReader bufferedReader) throws IOException {
        parseHeader(bufferedReader);
    }

    private void parseHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while ( line != null && !line.equals("")) {
            String[] currentLine = line.split(": ");
            httpHeader.put(currentLine[0], URLDecoder.decode(currentLine[1],"UTF-8"));
            line = bufferedReader.readLine();
        }
    }

    public String getHeaderByKey(String key) {
        return httpHeader.getOrDefault(key, "");
    }

}
