package view;

import model.Model;
import utils.io.FileIoUtils;

import java.io.IOException;

import static context.ApplicationContext.handlebars;
import static utils.mime.MimeTypeUtils.getMimeType;

public class View {
    private final byte[] content;
    private final String contentType;

    private View(byte[] content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public static View of(Model model, String viewPath) {
        String contentType = getMimeType(viewPath);
        if (model == null || model.isEmpty()) {
            return new View(getResource(viewPath), contentType);
        }
        return new View(resolveTemplate(model, viewPath), contentType);
    }

    private static byte[] getResource(String viewPath) {
        try {
            return FileIoUtils.loadFileFromClasspath(viewPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ViewResourcedLoadFailedException();
        }
    }

    private static byte[] resolveTemplate(Model model, String viewPath) {
        try {
            return handlebars.compile(viewPath)
                    .apply(model.getData()).getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ViewTemplateCompileFailedException();
        }
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
