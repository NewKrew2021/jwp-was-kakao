package webserver.http;

public enum FileExtension {
    html,css,js;

    public static FileExtension fromPath(String path) {
        try {
            return FileExtension.valueOf(path.substring(path.lastIndexOf(".") + 1));
        } catch( IllegalArgumentException e ){
            return null;
        }
    }
}
