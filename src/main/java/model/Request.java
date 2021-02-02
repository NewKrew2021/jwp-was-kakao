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

    public Request(InputStream in) throws IOException {
        InputStreamReader reader= new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        String requestLine=br.readLine();
        logger.debug("####HTTP Request Header 출력");
        parsePath(requestLine);
        requestLine = br.readLine();
        parseRequest(br, requestLine);
    }

    private void parseRequest(BufferedReader br, String requestLine) throws IOException {
        Map<String, String> header=new HashMap<>();
        while (requestLine !=null&&!requestLine.equals("")){
            logger.debug(requestLine);
            String[] token= requestLine.split("\\: ");
            header.put(token[0],token[1]);
            requestLine = br.readLine();
        }
        if(this.method.equals(RequestMethod.POST)){
            int contentLength = Integer.parseInt(header.get("Content-Length"));
            parameter.merge(new Parameter(parseParams(IOUtils.readData(br, contentLength))));
        }
        requestHeader = new RequestHeader(header);
    }

    private void parsePath(String path) {
        String[] token = path.split(" ");
        this.method =RequestMethod.stringToRequestMethod(token[0]);
        String[] pathToken = token[1].split("\\?");
        this.path = pathToken[0];
        if (pathToken.length > 1) {
            this.parameter = new Parameter(parseParams(pathToken[1]));
        }
    }

    private Map<String, String> parseParams(String paramString) {
        Map<String, String> map = new HashMap<>();
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

    public Map<String, String> getAllParameter(){
        return parameter.getAllParameter();
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return requestHeader.get(key);
    }

    public boolean isLogin(){
        String cookie= Optional.ofNullable(requestHeader.get("Cookie")).orElse("logined=false");
        return cookie.equals("logined=true");
    }
}
