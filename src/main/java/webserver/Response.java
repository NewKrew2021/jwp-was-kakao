package webserver;

import dto.ParamValue;
import model.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Response {
    private final HttpStatus httpStatus;
    private final Optional<ParamValue> httpValue;

    private Response(HttpStatus httpStatus, Optional<ParamValue> httpValue) {
        this.httpStatus = httpStatus;
        this.httpValue = httpValue;
    }

    public static Response of(HttpStatus httpStatus) {
        return new Response(httpStatus, Optional.empty());
    }

    public static Response of(HttpStatus httpStatus, ParamValue httpValue) {
        return new Response(httpStatus, Optional.of(httpValue));
    }

    public String descHttpStatusCode() {
        return httpStatus.descHttpStatusCode();
    }

    public List<String> getAddHttpDesc() {
        List<String> addHttpDesc = new ArrayList<>();

        httpValue.ifPresent(paramValue -> paramValue.getParamMap()
                .forEach((key, value) -> addHttpDesc.add(String.format("%s: %s \r\n", key, value)))
        );

        return addHttpDesc;
    }
}
