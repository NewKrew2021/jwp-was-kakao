package response;

import java.io.DataOutputStream;
import java.util.Map;

public interface ResponseStatus {

    /*
    default void responseStatus(DataOutputStream dos) {
        setStatus(dos);
    }
    */

    void setStatus(DataOutputStream dos);

}
