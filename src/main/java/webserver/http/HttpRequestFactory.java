package webserver.http;

import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class HttpRequestFactory {

    public HttpRequest create(Reader reader) {
        try {
            HttpRequestMessageReader messageReader = new HttpRequestMessageReader(reader);

            HttpRequestLine requestLine = readRequestLine(messageReader);
            List<HttpHeader> headers = readHeaders(messageReader);

            HttpRequest.Builder builder = HttpRequest.builder()
                    .requestLine(requestLine)
                    .headers(headers);

            if (isSupportBody(requestLine.getMethod()))
                builder.body(readBody(messageReader, contentLength(headers)));

            return builder.build();
        } catch (IOException e) {
            throw new FailToCreateHttpRequestException("HttpRequest 객체 생성에 실패했습니다", e);
        }
    }

    private final List<HttpMethod> SUPPORT_BODY_METHODS = Arrays.asList(HttpMethod.POST);

    private boolean isSupportBody(HttpMethod method) {
        return SUPPORT_BODY_METHODS.contains(method);
    }

    private int contentLength(List<HttpHeader> headers) {
        return Integer.valueOf(headers.stream()
                .filter(it -> it.getKey().equalsIgnoreCase("Content-Length"))
                .map(HttpHeader::getValue)
                .findFirst()
                .orElse("-1"));

    }

    private HttpRequestLine readRequestLine(HttpRequestMessageReader reader) throws IOException {
        return new HttpRequestLine(reader.readLine());
    }

    private List<HttpHeader> readHeaders(HttpRequestMessageReader reader) {
        return reader.linesToEOH()
                .map(it -> new HttpHeader(it))
                .collect(Collectors.toList());
    }

    private String readBody(HttpRequestMessageReader reader, int length) {
        try {
            if (length == 0) return "";

            if (length > 0) {
                return IOUtils.readData(reader, length);
            }
            return IOUtils.readAllRemainingData(reader);

        } catch (IOException e) {
            throw new FailToCreateHttpRequestException("http message body 를 읽는도중 문제가 발생했습니다", e);
        }
    }

    private class HttpRequestMessageReader extends BufferedReader {
        public HttpRequestMessageReader(Reader in) {
            super(in);
        }

        public Stream<String> linesToEOH() {
            Iterator<String> iter = new Iterator() {
                String nextLine = null;

                @Override
                public boolean hasNext() {
                    if (nextLine != null) return true;

                    try {
                        nextLine = readLine();
                        return !isEOH(nextLine);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }

                @Override
                public String next() {
                    if (nextLine != null || hasNext()) {
                        String line = nextLine;
                        nextLine = null;
                        return line;
                    } else {
                        throw new NoSuchElementException();
                    }
                }

                private boolean isEOH(String nextLine){
                    return StringUtils.isEmpty(nextLine);
                }
            };
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                    iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
        }
    }

}
