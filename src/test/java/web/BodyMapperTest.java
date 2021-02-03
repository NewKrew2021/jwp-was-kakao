package web;

import exception.MapperNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BodyMapperTest {

    @Test
    void bytesToString() {
        String given = "a b c 1 3 5 ! # %";

        String result = BodyMapper.read(given.getBytes(), String.class);

        assertThat(result).isEqualTo(given);
    }

    @Test
    void fail() {
        assertThatThrownBy(() -> BodyMapper.read("".getBytes(), NotExistClass.class))
                .isInstanceOf(MapperNotFoundException.class)
                .hasMessage("NotExistClass 클래스의 mapper가 존재하지 않습니다");
    }

    class NotExistClass {
    }
}
