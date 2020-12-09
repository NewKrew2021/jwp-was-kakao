package webserver.http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

abstract class HttpSessionTest {

    HttpSession httpSession;

    @DisplayName("attribute 를 저장하고 조회 할 수 있다")
    @Test
    void setAttribute(){
        httpSession.setAttribute("userName", "nio");
        assertThat(httpSession.getAttribute("userName")).isEqualTo("nio");
    }

    @DisplayName("attribute 를 삭제 할 수 있다")
    @Test
    void removeAttribute(){
        httpSession.setAttribute("userName", "nio");
        httpSession.setAttribute("email", "sehan.choi@gmail.com");

        httpSession.removeAttribute("userName");

        assertThat(httpSession.getAttribute("userName")).isNull();
        assertThat(httpSession.getAttribute("email")).isEqualTo("sehan.choi@gmail.com");
    }

    @DisplayName("모든 attribute 를 삭제 할 수 있다")
    @Test
    void invalidate(){
        httpSession.setAttribute("userName", "nio");
        httpSession.setAttribute("email", "sehan.choi@gmail.com");

        httpSession.invalidate();

        assertThat(httpSession.getAttribute("userName")).isNull();
        assertThat(httpSession.getAttribute("email")).isNull();
    }

}

class SimpleHttpSessionTest extends HttpSessionTest {

    @BeforeEach
    void setUp(){
        httpSession = new SimpleHttpSession();
    }

}