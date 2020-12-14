package webserver.http;

import utils.IOUtils;
import webserver.config.ServerConfigConstants;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {

    private final HttpHeader httpHeader;
    private final RequestBody body;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        httpHeader = HttpHeader.from(bufferedReader);
        // 요구사항 1
        httpHeader.print();
        if (getMethod().equals(HttpMethod.POST) || getMethod().equals(HttpMethod.DELETE) || getMethod().equals(HttpMethod.PUT)) {
            body = RequestBody.from(IOUtils.readData(bufferedReader, Integer.parseInt(getContentLength())));
        } else if(getMethod().equals(HttpMethod.GET)) {
            body = RequestBody.from(getQueryString());
        } else{
            body = RequestBody.from("");
        }
    }

    public HttpHeader getHeader() {
        return httpHeader;
    }

    public HttpMethod getMethod() {
        return HttpMethod.valueOf(httpHeader.getHttpHeader(HttpHeader.METHOD));
    }

    public String getPath() {
        return httpHeader.getHttpHeader(HttpHeader.PATH);
    }

    public String getQueryString() {
        String[] paths = httpHeader.getHttpHeader(HttpHeader.PATH).split("\\?");
        if(paths.length > 1){
            return paths[1];
        }else{
            return "";
        }
    }

    public String getHost() {
        return httpHeader.getHttpHeader(HttpHeader.HOST);
    }

    public String getConnection() {
        return httpHeader.getHttpHeader(HttpHeader.CONNECTION);
    }

    public String getAccept() {
        return httpHeader.getHttpHeader(HttpHeader.ACCEPT);
    }

    public String getContentLength() {
        return httpHeader.getHttpHeader(HttpHeader.CONTENT_LENGTH);
    }

    public RequestBody getBody() {
        return body;
    }

    public boolean isLogin(){
        return httpHeader.getCookies().get(ServerConfigConstants.LOGIN_COOKIE_KEY) != null
                && httpHeader.getCookies().get(ServerConfigConstants.LOGIN_COOKIE_KEY).equals("true");
    }

}
