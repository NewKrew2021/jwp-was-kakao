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

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);
    private RequestMethod method;
    private String path;
    private Map<String, String> parameter = new HashMap<>();
    private Map<String, String> header = new HashMap<>();

    public Request(InputStream in) throws IOException {
        InputStreamReader reader= new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        String str=br.readLine();
        logger.debug("####HTTP Request Header 출력");
        parsePath(str);
        str = br.readLine();
        while (str!=null&&!str.equals("")){
            logger.debug(str);
            String[] token=str.split("\\: ");
            header.put(token[0],token[1]);
            str = br.readLine();
        }
        if(this.method.equals(RequestMethod.POST)){
            parameter.putAll(parseParams(IOUtils.readData(br,Integer.parseInt(header.get("Content-Length")))));
        }
    }

    public void parsePath(String path) {
        String[] token = path.split(" ");
        this.method =RequestMethod.stringToRequestMethod(token[0]);
        String[] pathToken = token[1].split("\\?");
        this.path = pathToken[0];
        if (pathToken.length > 1) {
            this.parameter = parseParams(pathToken[1]);
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

    public Map<String,String> getAllParameter(){
        return parameter;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public boolean isLogin(){
        return header.get("Cookie").equals("logined=true");
    }
}
