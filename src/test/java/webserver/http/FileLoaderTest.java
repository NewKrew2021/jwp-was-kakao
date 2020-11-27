package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ClasspathFileLoader;
import utils.FileLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

abstract class FileLoaderTest {

    FileLoader fileLoader;

    @DisplayName("classPath 에 있는 uri 를 load 해서 byte[] 로 return 한다")
    @Test
    void load(){
        byte[] content = fileLoader.load("/uri-1.html");

        assertThat(content).isEqualTo("uri-1 content".getBytes());
    }

    @DisplayName("uri 가 존재하지 않으면 exception 을 던진다")
    @Test
    void notExist(){
        assertThatThrownBy(() -> fileLoader.load("/notExist.file"))
                .isInstanceOf(RuntimeException.class);
    }

}

class ClasspathFileLoaderTest extends FileLoaderTest {

    @BeforeEach
    void setUp(){
        fileLoader = new ClasspathFileLoader("./templates");
    }
}