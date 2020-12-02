package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestFactoryTest {

    @DisplayName("GET 요청메세지로 HttpRequest 를 생성할 수 있다")
    @Test
    void createGetHttpRequest(){
        String requestMessage = String.join("\r\n", Arrays.asList(
                "GET /user/search?name=nio&email=nio@kakaocorp.com HTTP/1.1",
                "Host: localhost:8080",
                "Connection: Keep-Alive",
                "User-Agent: Apache-HttpClient/4.5.12 (Java/11.0.8)",
                "Accept-Encoding: gzip,deflate"
        ));
        StringReader reader = new StringReader(requestMessage);

        HttpRequestFactory factory = new HttpRequestFactory();
        HttpRequest httpRequest = factory.create(reader);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getRequestLine()).isEqualTo("GET /user/search?name=nio&email=nio@kakaocorp.com HTTP/1.1");
        assertThat(httpRequest.getPath()).isEqualTo("/user/search");
        assertThat(httpRequest.getHeaders()).containsExactlyInAnyOrder(
                new HttpHeader("Host: localhost:8080"),
                new HttpHeader("Connection: Keep-Alive"),
                new HttpHeader("User-Agent: Apache-HttpClient/4.5.12 (Java/11.0.8)"),
                new HttpHeader("Accept-Encoding: gzip,deflate")
        );
    }

    @DisplayName("POST 요청메세지로 HttpRequest 를 생성 할 수 있다")
    @Test
    void createPostHttpRequest(){
        String body = "name=nio&email=nio@kakaocorp.com";
        String requestMessage = String.join("\r\n", Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: Keep-Alive",
                "Content-Length: " + body.length(),
                "User-Agent: Apache-HttpClient/4.5.12 (Java/11.0.8)",
                "",
                body
        ));

        System.out.print(requestMessage);
        StringReader reader = new StringReader(requestMessage);

        HttpRequestFactory factory = new HttpRequestFactory();
        HttpRequest httpRequest = factory.create(reader);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getRequestLine()).isEqualTo("POST /user/create HTTP/1.1");
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeaders()).containsExactlyInAnyOrder(
                new HttpHeader("Host: localhost:8080"),
                new HttpHeader("Connection: Keep-Alive"),
                new HttpHeader("Content-Length: " + body.length()),
                new HttpHeader("User-Agent: Apache-HttpClient/4.5.12 (Java/11.0.8)")
        );
        assertThat(httpRequest.getBody()).isEqualTo("name=nio&email=nio@kakaocorp.com");
    }

    @DisplayName("POST 요청메세지에 Content-Length 헤더가 없으면 끝까지 읽는다")
    @Test
    void createPostHttpRequest2(){
        String body = "name=nio&email=nio@kakaocorp.com";
        String requestMessage = String.join("\r\n", Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Content-Type: application/x-www-form-urlencoded",
                "",
                body
        ));
        StringReader reader = new StringReader(requestMessage);

        HttpRequestFactory factory = new HttpRequestFactory();
        HttpRequest httpRequest = factory.create(reader);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getRequestLine()).isEqualTo("POST /user/create HTTP/1.1");
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeaders()).containsExactlyInAnyOrder(
                new HttpHeader("Host: localhost:8080"),
                new HttpHeader("Content-Type: application/x-www-form-urlencoded")
        );
        assertThat(httpRequest.getBody()).isEqualTo("name=nio&email=nio@kakaocorp.com");
    }



}