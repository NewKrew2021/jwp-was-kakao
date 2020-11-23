package webserver.http;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpRequestUriLoader {

    byte[] load(String uri);

    static HttpRequestUriLoader fileLoader(String basePath) {
        return new FileLoader(basePath);
    }
}

class FileLoader implements HttpRequestUriLoader {

    private final String basePath;

    public FileLoader(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public byte[] load(String uri) {
        try {
            return FileIoUtils.loadFileFromClasspath(basePath + uri);
        } catch (IOException e) {
            throw new FailToLoadHttpRequestUriException("classpath 에서 " + basePath + uri + " 로딩중 문제가 발생했습니다.", e);
        } catch (URISyntaxException e) {
            throw new FailToLoadHttpRequestUriException("classpath 에서 " + basePath + uri + " 로딩중 문제가 발생했습니다.", e);
        } catch (RuntimeException e) {
            throw new FailToLoadHttpRequestUriException("classpath 에서 " + basePath + uri + " 로딩중 문제가 발생했습니다.", e);
        }
    }

}