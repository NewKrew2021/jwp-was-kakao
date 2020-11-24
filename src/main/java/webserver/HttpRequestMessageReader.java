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

/**
 * Reader 에서 Http 
 */
public class HttpRequestMessageReader extends BufferedReader {
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
