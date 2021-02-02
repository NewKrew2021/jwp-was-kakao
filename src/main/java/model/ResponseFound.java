package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ResponseFound extends Response {
    private final String url;
    private final boolean isLogined;

    private ResponseFound(String url, boolean isLogined) {
        this.url = url;
        this.isLogined = isLogined;
    }

    public static ResponseFound from(String url) {
        return new ResponseFound(url, false);
    }

    public static Response of(String url, boolean isLogined) {
        return new ResponseFound(url, isLogined);
    }

    @Override
    public void write(DataOutputStream dos) {
        response302Header(dos);
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("Set-Cookie: logined=" + isLogined + ";Path=/\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseFound that = (ResponseFound) o;
        return isLogined == that.isLogined && url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, isLogined);
    }
}
