package webserver.http;

import java.util.List;

public interface HttpRequestDataObjectValidator {
    void validate(List<HttpRequestParam> params);
}
