package webserver;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class HttpHeaderReader extends BufferedReader {
    public HttpHeaderReader(Reader in) {
        super(in);
    }

    @Override
    public Stream<String> lines() {
        Iterator<String> iter = new Iterator() {
            String nextLine = null;

            @Override
            public boolean hasNext() {
                if (!StringUtils.isEmpty(nextLine)) return true;

                try {
                    nextLine = readLine();
                    return !StringUtils.isEmpty(nextLine);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public String next() {
                if (!isEOH(nextLine) || hasNext()) {
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
