package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MimeTypeTest {

    @DisplayName("파일확장자에 매핑된  MimeType 을 찾을 수 있다")
    @Test
    void test(){
        assertThat(MimeType.fromExtenstion("html")).isEqualTo(MimeType.TEXT_HTML);
        assertThat(MimeType.fromExtenstion("htm")).isEqualTo(MimeType.TEXT_HTML);
        assertThat(MimeType.fromExtenstion("css")).isEqualTo(MimeType.TEXT_CSS);
        assertThat(MimeType.fromExtenstion("js")).isEqualTo(MimeType.APPLICATION_JS);
    }

}