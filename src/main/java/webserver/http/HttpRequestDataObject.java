package webserver.http;

import java.util.List;

public interface HttpRequestDataObject {

    List<HttpRequestDataObjectValidator> getValidators();

    default void validate(List<HttpRequestParam> params) {
        getValidators().forEach(it -> it.validate(params));
    }

    default String getValue(List<HttpRequestParam> params, String paramName) {
        return params.stream()
                .filter(it -> paramName.equals(it.getName()))
                .findFirst()
                .get()
                .getValue();
    }

}
