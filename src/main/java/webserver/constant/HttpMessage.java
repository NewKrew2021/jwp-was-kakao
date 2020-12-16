package webserver.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HttpMessage {

    public static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";
    public static final Set<String> SUPPORTED_HTTP_VERSION_SET = new HashSet<>(
            Arrays.asList("HTTP/1.0", "HTTP/1.1")
    );

    public static final String CRLF = "\r\n";
    public static final String SP = " ";

}
