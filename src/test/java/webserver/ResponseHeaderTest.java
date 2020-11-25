package webserver;

import org.junit.jupiter.api.Test;
import webserver.request.ContentType;
import webserver.request.Protocol;
import webserver.request.Status;
import webserver.response.ResponseHeader;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHeaderTest {
    @Test
    public void status200() {
        ResponseHeader header = ResponseHeader.builder().protocol(Protocol.HTTP).status(Status.OK).build();

        assertThat(header.toString()).isEqualTo("HTTP/1.1 200 OK \r\n\r\n");
    }

    @Test
    public void status302() {
        ResponseHeader header = ResponseHeader.builder().protocol(Protocol.HTTP).status(Status.REDIRECT).build();

        assertThat(header.toString()).isEqualTo("HTTP/1.1 302 FOUND \r\n\r\n");
    }

    @Test
    public void cookie() {
        ResponseHeader header = ResponseHeader.builder().cookie(Cookie.login()).build();

        assertThat(header.toString()).isEqualTo("Set-Cookie: logined=true; Path=/\r\n\r\n");
    }

    @Test
    public void location() {
        ResponseHeader header = ResponseHeader.builder().location("http://localhost:8080/index.html").build();

        assertThat(header.toString()).isEqualTo("Location: http://localhost:8080/index.html\r\n\r\n");
    }

    @Test
    public void contentType() {
        ResponseHeader header = ResponseHeader.builder().contentType(ContentType.HTML).build();

        assertThat(header.toString()).isEqualTo("Content-Type: text/html;charset=utf-8\r\n\r\n");
    }

    @Test
    public void contentTypeCss() {
        ResponseHeader header = ResponseHeader.builder().contentType(ContentType.CSS).build();

        assertThat(header.toString()).isEqualTo("Content-Type: text/css\r\n\r\n");
    }

    @Test
    public void contentLength() {
        ResponseHeader header = ResponseHeader.builder().contentLength(30).build();

        assertThat(header.toString()).isEqualTo("Content-Length: 30\r\n\r\n");
    }
}


//        }
//                dos.writeBytes("Set-Cookie: logined=false; Path=/" + "\r\n");
//                }
//
//private void response302Header(DataOutputStream dos, String location) {
//        try {
//        dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
//        dos.writeBytes("Location: " + location + "\r\n");
//        dos.writeBytes("\r\n");
//        } catch (IOException e) {
//        logger.error(e.getMessage());
//        }
//        }
//
//private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
//        try {
//        dos.writeBytes("HTTP/1.1 200 OK \r\n");
//        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
//        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
//        dos.writeBytes("\r\n");
//        } catch (IOException e) {
//        logger.error(e.getMessage());
//        }
//        }
//
//private void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
//        try {
//        dos.writeBytes("HTTP/1.1 200 OK \r\n");
//        dos.writeBytes("Content-Type: text/css\r\n");
//        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
