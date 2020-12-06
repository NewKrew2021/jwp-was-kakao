package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

abstract class ResourceLoaderTest {

    ResourceLoader resourceLoader;

    @DisplayName("classPath 에 있는 resource 를 읽어들여 문자열로 return 한다")
    @Test
    void load(){
        String content = resourceLoader.load("/test.txt");

        assertThat(content).isEqualTo("test");
    }

    @DisplayName("resource 가 존재하지 않으면 exception 을 던진다")
    @Test
    void notExist(){
        assertThatThrownBy(() -> resourceLoader.load("/notExist.file"))
                .isInstanceOf(RuntimeException.class);
    }

}

class ClasspathResourceLoaderTest extends ResourceLoaderTest {
    @BeforeEach
    void setUp(){
        resourceLoader = new ClasspathResourceLoader("./templates");
    }
}