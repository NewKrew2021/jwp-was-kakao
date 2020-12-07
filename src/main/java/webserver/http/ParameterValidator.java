package webserver.http;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParameterValidator {

    public static void validate(HttpRequest httpRequest, String ... params) {
        String absentParameters = Stream.of(params)
                .filter(param -> httpRequest.getParameter(param) == null)
                .collect(Collectors.joining(", "));

        if (!absentParameters.isEmpty()) {
            throw new IllegalArgumentException(absentParameters + " 가 꼭 필요해요");
        }
    }
}
