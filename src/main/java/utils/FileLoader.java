package utils;

import webserver.http.FailToLoadHttpRequestUriException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface FileLoader {

    byte[] load(String path);

}

