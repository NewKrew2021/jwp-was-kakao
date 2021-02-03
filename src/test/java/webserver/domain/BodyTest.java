package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BodyTest {
    @Test
    @DisplayName("콘텐츠 타입 테스트 - html")
    void htmlTest() {
        Body body = new Body("/index.html");

        assertThat(body.getContentType()).isEqualTo("text/html");
    }

    @Test
    @DisplayName("콘텐츠 타입 테스트 - js")
    void jsTest() {
        Body body = new Body("/js/jquery-2.2.0.min.js");

        assertThat(body.getContentType()).isEqualTo("text/javascript");
    }

    @Test
    @DisplayName("콘텐츠 타입 테스트 - woff")
    void xmlTest() {
        Body body = new Body("/fonts/glyphicons-halflings-regular.woff");

        assertThat(body.getContentType()).isEqualTo("font/woff");
    }

    @Test
    @DisplayName("콘텐츠 타입 테스트 - css")
    void cssTest() {
        Body body = new Body("/css/bootstrap.min.css");

        assertThat(body.getContentType()).isEqualTo("text/css");
    }

    @Test
    @DisplayName("콘텐츠 타입 테스트 - ico")
    void icoTest() {
        Body body = new Body("/favicon.ico");

        assertThat(body.getContentType()).isEqualTo("image/png");
    }

    @Test
    @DisplayName("콘텐츠 타입 테스트 - png")
    void pngTest() {
        Body body = new Body("/images/80-text.png");

        assertThat(body.getContentType()).isEqualTo("image/png");
    }

    @Test
    @DisplayName("콘텐츠 타입 테스트 - svg")
    void svgTest() {
        Body body = new Body("/fonts/glyphicons-halflings-regular.svg");

        assertThat(body.getContentType()).isEqualTo("image/svg+xml");
    }

    @Test
    @DisplayName("콘텐츠 타입 테스트 - eot")
    void eotTest() {
        Body body = new Body("/fonts/glyphicons-halflings-regular.eot");

        assertThat(body.getContentType()).isEqualTo("font/eot");
    }

    @Test
    @DisplayName("콘텐츠 타입 테스트 - ttf")
    void ttfTest() {
        Body body = new Body("/fonts/glyphicons-halflings-regular.ttf");

        assertThat(body.getContentType()).isEqualTo("font/ttf");
    }

    @Test
    @DisplayName("콘텐츠 타입 테스트 - woff2")
    void woff2Test() {
        Body body = new Body("/fonts/glyphicons-halflings-regular.woff2");

        assertThat(body.getContentType()).isEqualTo("font/woff2");
    }

}