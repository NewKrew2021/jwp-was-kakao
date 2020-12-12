package webserver.request;

import java.util.Arrays;

public enum Protocol {
    HTTP("HTTP/1.1");

    private final String message;

    Protocol(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static Protocol fromMessage(String message) {
        return Arrays.stream(values())
                .filter(protocol -> protocol.getMessage().equals(message))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("맞는 프로토콜이 없습니다."));
    }
}
