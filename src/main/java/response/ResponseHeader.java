package response;

import java.io.DataOutputStream;
import java.util.Map;

public interface ResponseHeader {
    void responseHeader(DataOutputStream dos, Map<String, String> response);
}
