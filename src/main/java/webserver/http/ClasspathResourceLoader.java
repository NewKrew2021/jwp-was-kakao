package webserver.http;

import utils.FileIoUtils;

public class ClasspathResourceLoader implements ResourceLoader {

    private final String basePath;

    public ClasspathResourceLoader(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public String load(String path) {
        try {
            return new String(FileIoUtils.loadFileFromClasspath(basePath + path));
        } catch (Exception e) {
            throw new FailToLoadHttpRequestUriException("classpath 에서 " + basePath + path + " 로딩중 문제가 발생했습니다.", e);
        }
    }

}
