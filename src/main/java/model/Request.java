package model;

import annotation.web.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);
    private RequestMethod method;
    private String path;
    private Parameter parameter;
    private RequestHeader requestHeader;

    public Request(RequestMethod method, String path, Parameter parameter, RequestHeader requestHeader) {
        this.method = method;
        this.path = path;
        this.parameter = parameter;
        this.requestHeader = requestHeader;
    }

    public static Request of(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String[] split = br.readLine().split(" ");

        RequestMethod method = RequestMethod.valueOf(split[0]);

        String[] splitPath = split[1].split("\\?");
        String paramString = splitPath.length > 1 ? splitPath[1] : "";

        Map<String, String> parameter = parseParams(paramString);

        RequestHeader header = new RequestHeader(parseHeader(br));

        if(method.equals(RequestMethod.POST)){
            parameter.putAll(parseBody(br, header));
        }

        return new Request(method, splitPath[0], new Parameter(parameter), header);
    }

    private static Map<String, String> parseHeader(BufferedReader br) throws IOException {
        Map<String, String> header = new HashMap<>();
        String curr = br.readLine();
        while (curr != null && !curr.equals("")) {
            String[] token = curr.split("\\: ");
            header.put(token[0], token[1]);
            curr = br.readLine();
        }
        return header;
    }

    private static Map<String, String> parseBody(BufferedReader br, RequestHeader header) throws IOException {
        int contentLength = Integer.parseInt(header.get("Content-Length"));
        return parseParams(IOUtils.readData(br, contentLength));
    }

    private static Map<String, String> parseParams(String paramString) {
        Map<String, String> map = new HashMap<>();
        if(!paramString.contains("=")){
            return map;
        }
        for (String path : paramString.split("\\&")) {
            String[] temp = path.split("=");
            map.put(temp[0], temp[1]);
        }
        return map;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }

    public Map<String, String> getAllParameter() {
        return parameter.getAllParameter();
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return requestHeader.get(key);
    }

    public boolean isLogin() {
        String cookie = Optional.ofNullable(requestHeader.get("Cookie")).orElse("logined=false");
        return cookie.equals("logined=true");
    }
}
