package webserver.http;

import com.google.common.base.Charsets;
import webserver.http.utils.FileExtentions;

import java.util.Map;

public class StaticResourceView implements View {

    private ResourceLoader resourceLoader;
    private String viewName;

    public StaticResourceView(ResourceLoader resourceLoader, String viewName) {
        this.resourceLoader = resourceLoader;
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) {
        String content = resourceLoader.load(viewName);
        httpResponse.setStatus(HttpStatus.x200_OK);
        httpResponse.setContentType(
                MimeType.fromExtenstion(FileExtentions.fromPath(viewName)),
                Charsets.UTF_8);
        httpResponse.setBody(content.getBytes());
        httpResponse.send();
    }
}
