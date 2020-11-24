package webserver.http;

import utils.IOUtils;
import webserver.HttpRequestMessageReader;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

public class HttpRequestFactory {

    public HttpRequest create(Reader reader){
        try {
            HttpRequestMessageReader messageReader = new HttpRequestMessageReader(reader);
            HttpRequest.Builder builder = HttpRequest.builder().requestLine(readRequestLine(messageReader));

            List<HttpHeader> headers = readHeaders(messageReader);
            builder.headers(headers);

            builder.body(readBody(messageReader, contentLength(headers)));
            return builder.build();
        } catch ( IOException e ){
            throw new FailToCreateHttpRequestException("HttpRequest 객체 생성에 실패했습니다", e);
        }
    }

    private int contentLength(List<HttpHeader> headers) {
        return Integer.valueOf( headers.stream()
                .filter(it -> it.getKey().equalsIgnoreCase("Content-Length"))
                .map(HttpHeader::getValue)
                .findFirst()
                .orElse("0"));

    }

    private String readRequestLine(HttpRequestMessageReader reader) throws IOException {
        return reader.readLine();
    }

    private List<HttpHeader> readHeaders(HttpRequestMessageReader reader) {
        return reader.linesToEOH()
                .map(it -> new HttpHeader(it))
                .collect(Collectors.toList());
    }

    private String readBody(HttpRequestMessageReader reader, int length) {
        try {
            if( length > 0 ) {
                reader.readLine();
                return IOUtils.readData(reader, length);
            }
            return "";
        } catch (IOException e) {
            throw new FailToCreateHttpRequestException("http message body 를 읽는도중 문제가 발생했습니다", e);
        }
    }


}
