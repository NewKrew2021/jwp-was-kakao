package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeaderTest {

    @DisplayName("[key]:[value] 형식의 문자열로 HttpHeader 객체를 생성할 수 있디")
    @Test
    void create(){
        HttpHeader header = new HttpHeader("Content-Length: 59");

        assertThat(header).isEqualTo(new HttpHeader("Content-Length", "59"));
    }

}