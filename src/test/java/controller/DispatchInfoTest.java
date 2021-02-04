package controller;

import http.HttpRequest;
import http.HttpRequestParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class DispatchInfoTest {
    public static final String PATH = "./src/test/resources/";

    @DisplayName("Template request일 경우 true")
    @Test
    void matchWithTemplateRequest() throws IOException {
        DispatchInfo dispatchInfo = DispatchInfo.Template;
        HttpRequest request = new HttpRequestParser(IOUtils.readRequest(new FileInputStream(PATH + "index-html.txt"))).parse();

        assertThat(dispatchInfo.matchWithTemplateRequest(request)).isTrue();
    }

    @DisplayName("Static request일 경우 true")
    @Test
    void matchWithStaticRequest() throws IOException {
        DispatchInfo dispatchInfo = DispatchInfo.Static;
        HttpRequest request = new HttpRequestParser(IOUtils.readRequest(new FileInputStream(PATH + "css.txt"))).parse();

        assertThat(dispatchInfo.matchWithStaticRequest(request)).isTrue();
    }

    @DisplayName("일치하는 request일 경우 true")
    @Test
    void matchWithRequest() throws IOException {
        DispatchInfo dispatchInfo = DispatchInfo.UserCreate;
        HttpRequest request = new HttpRequestParser(IOUtils.readRequest(new FileInputStream(PATH + "post-user-create.txt"))).parse();

        assertThat(dispatchInfo.matchWithRequest(request)).isTrue();
    }

    @DisplayName("매치하는 request일 경우 true")
    @Test
    void matchWith() throws IOException{
        DispatchInfo dispatchInfo = DispatchInfo.UserCreate;
        HttpRequest request = new HttpRequestParser(IOUtils.readRequest(new FileInputStream(PATH + "post-user-create.txt"))).parse();

        assertThat(dispatchInfo.matchWith(request)).isTrue();
    }
}