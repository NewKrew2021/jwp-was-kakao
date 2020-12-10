package webserver.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * * String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
 * * void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
 * * Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
 * * void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
 * * void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제
 */
public class HttpSessionTest {
    @DisplayName("세션 생성시 반드시 세션아이디가 존재한다")
    @Test
    void createNullSessionId() {
        Assertions.assertThatThrownBy(() -> new HttpSession(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
