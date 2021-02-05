package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecodeUtilsTest {
    @Test
    void decodeUrl() {
        String url = "userId=yuni&password=1234&name=%EC%9C%A4%EB%8C%80%EC%8A%B9&email=yuni.code%40kakaocorp.com";
        assertThat(DecodeUtils.decodeUrl(url)).isEqualTo("userId=yuni&password=1234&name=윤대승&email=yuni.code@kakaocorp.com");
    }
}