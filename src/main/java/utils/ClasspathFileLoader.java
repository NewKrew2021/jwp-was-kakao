package utils;

import webserver.http.FailToLoadHttpRequestUriException;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClasspathFileLoader implements FileLoader {

    private final String basePath;

    public ClasspathFileLoader(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public byte[] load(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath(basePath + path);
        } catch (IOException e) {
            throw new FailToLoadHttpRequestUriException("classpath 에서 " + basePath + path + " 로딩중 문제가 발생했습니다.", e);
        } catch (URISyntaxException e) {
            throw new FailToLoadHttpRequestUriException("classpath 에서 " + basePath + path + " 로딩중 문제가 발생했습니다.", e);
        } catch (RuntimeException e) {
            throw new FailToLoadHttpRequestUriException("classpath 에서 " + basePath + path + " 로딩중 문제가 발생했습니다.", e);
        }
    }

}
