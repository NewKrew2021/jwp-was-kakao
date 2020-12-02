package utils;

import webserver.http.FailToLoadHttpRequestUriException;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClasspathResourceLoader implements ResourceLoader {

    private final String basePath;

    public ClasspathResourceLoader(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public String load(String path) {
        try {
            return new String(FileIoUtils.loadFileFromClasspath(basePath + path));
        } catch (IOException e) {
            throw new FailToLoadHttpRequestUriException("classpath 에서 " + basePath + path + " 로딩중 문제가 발생했습니다.", e);
        } catch (URISyntaxException e) {
            throw new FailToLoadHttpRequestUriException("classpath 에서 " + basePath + path + " 로딩중 문제가 발생했습니다.", e);
        } catch (RuntimeException e) {
            throw new FailToLoadHttpRequestUriException("classpath 에서 " + basePath + path + " 로딩중 문제가 발생했습니다.", e);
        }
    }

}
