package utils.mime;

import javax.activation.MimetypesFileTypeMap;
import java.io.IOException;

public class MimeTypeUtils {
    private static MimetypesFileTypeMap map;

    private static MimetypesFileTypeMap getInstance() throws IOException {
        if (map == null) {
            map = new MimetypesFileTypeMap(MimeTypeUtils.class.getClassLoader().getResource(".mime.types").openStream());
        }
        return map;
    }

    public static String getMimeType(String filePath) {
        try {
            return getInstance().getContentType(filePath);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new MimeTypeFileNotFoundException();
        }
    }
}
