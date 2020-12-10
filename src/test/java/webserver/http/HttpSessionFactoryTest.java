package webserver.http;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * v 세션객체를 생성한다
 * * 세션아이디가 존재하면 동일한 객체를 리턴한다
 * * 주어진 세션아이디를 가진 세션객체를 생성한다.
 * * 세션아이디가 존재하지 않거나 널이면 새로운 객체를 리턴한다
 */
public class HttpSessionFactoryTest {

    private HttpSessionFactory httpSessionFactory;

    @BeforeEach
    void setUp() {
        httpSessionFactory = new HttpSessionFactory();
    }

    @DisplayName("세션 아이디가 널이면 새로운 세션을 생성한다")
    @Test
    void createSessionWithNullId() {
        assertThat(httpSessionFactory.getOrCreate(null)).isNotEqualTo(httpSessionFactory.getOrCreate(null));
    }

    @DisplayName("세션 아이디가 같으면 같은 객체를 반환한다")
    @Test
    void createSessionWithExistingId() {
        String id = "session id";
        assertThat(httpSessionFactory.getOrCreate(id)).isEqualTo(httpSessionFactory.getOrCreate(id));
    }

    @DisplayName("주어진 세션아이디를 가진 세션객체를 생성한다")
    @Test
    void createSessionWithGivenId() {
        String id = "session id";
        assertThat(httpSessionFactory.getOrCreate(id).getId()).isEqualTo(id);
    }

}