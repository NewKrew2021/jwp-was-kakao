package model;

import exception.http.IllegalHeaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {
    private final Map<String, String> headerMap = new HashMap<>();

    public HttpRequestHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!(line == null || line.trim().equals(""))) {
            String[] headerLine = line.split(":", 2);
            validateHeaderLine(headerLine);
            headerMap.put(headerLine[0].trim(), headerLine[1].trim());
            line = br.readLine();
        }
    }

    private void validateHeaderLine(String[] headerLine) {
        if (headerLine.length < 2) {
            throw new IllegalHeaderException();
        }
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String getHeader(String key){
        String header = headerMap.get(key);
        if(header == null) {
            throw new IllegalHeaderException();
        }
        return header;
    }
}
