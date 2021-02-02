package request;

import annotation.web.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    RequestUri requestUri;
    RequestHeader requestHeader;
    RequestBody requestBody;


    public HttpRequest(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        String uriLine = br.readLine();
        logger.debug(uriLine);
        createRequestURI(uriLine);
        createRequestHeader(br);
        createRequestBody(br);
    }

    public Optional<String> getParam(String key) { return requestHeader.getHeaderValue(key);}

    public String getUri(){
        return requestUri.getUri();
    }

    public Map<String, String> getParams(){
        return requestUri.getParams();
    }

    public Map<String, String> getBody(){
        return requestBody.getBody();
    }

    public RequestMethod getRequestMethod(){
        return requestUri.getRequestMethod();
    }


    private void createRequestURI(String line){
        String[] splitLine = line.split(" ");
        this.requestUri = new RequestUri(getMethodType(splitLine[0]), extractPath(splitLine[1]), extractParams(splitLine[1]));
    }

    private void createRequestHeader(BufferedReader br) throws IOException {
        Map<String, String> requestHeader = new HashMap<>();
        String tempLine;
        while(!(tempLine = br.readLine()).equals("")){
            String[] splitTempLine = tempLine.split(":");
            requestHeader.put(splitTempLine[0].trim(), splitTempLine[1].trim());
            logger.debug("header: {}", tempLine);
        }
        this.requestHeader = new RequestHeader(requestHeader);
    }

    private void createRequestBody(BufferedReader br) throws IOException {
        Optional<Integer> contentLength = requestHeader.getContentLength();
        if(contentLength.isPresent()){
            this.requestBody = new RequestBody(
                    requestStringToMap(
                            IOUtils.readData(br, contentLength.get())));
        }
    }

    private RequestMethod getMethodType(String line){
        String method = line.split(" ")[0];
        if(method.equals("GET")){
            return RequestMethod.GET;
        }
        if (method.equals("POST")){
            return RequestMethod.POST;
        }
        if (method.equals("DELETE")){
            return RequestMethod.DELETE;
        }
        if(method.equals("PUT")){
            return RequestMethod.PUT;
        }
        if(method.equals("PATCH")){
            return RequestMethod.PATCH;
        }
        throw new RuntimeException();
    }

    private String extractPath(String uri){
        return uri.split("\\?")[0];
    }

    private Map<String, String> extractParams(String uri){
        String[] tmp =  uri.split("\\?");
        if(tmp.length <= 1){
            return null;
        }
        return requestStringToMap(tmp[1]);
    }

    private Map<String, String> requestStringToMap(String line){
        Map<String, String> result = new HashMap<>();
        String[] splitString = line.split("&");
        for(String pair: splitString){
            String[] splitPair = pair.split("=");
            result.put(splitPair[0], splitPair[1]);
        }
        return result;
    }



}
