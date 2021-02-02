package response;

import java.io.DataOutputStream;
import java.util.Map;

public interface ResponseHeader {

    String KEY_VALUE_REGEX = ": ";
    String NEW_LINE_PREFIX = "\r\n";

    void responseHeader(DataOutputStream dos, Map<String, String> response);
}
