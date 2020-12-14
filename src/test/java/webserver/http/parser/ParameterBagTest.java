package webserver.http.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.ParameterBag;

import static org.assertj.core.api.Assertions.*;

class ParameterBagTest {


    @Test
    @DisplayName("값이 없을 때 ")
    void null_or_empty() {
        ParameterBag parameterBag = ParameterBagParser.parse("");
        assertThat(parameterBag.getParameter("param1")).isEqualTo(null);
        assertThat(parameterBag.getParameter("param2")).isEqualTo(null);
        assertThat(parameterBag.getParameter("param3")).isEqualTo(null);

        ParameterBag parameterBag2 = ParameterBagParser.parse(null);
        assertThat(parameterBag2.getParameter("param1")).isEqualTo(null);
        assertThat(parameterBag2.getParameter("param2")).isEqualTo(null);
        assertThat(parameterBag2.getParameter("param3")).isEqualTo(null);
    }

    @Test
    @DisplayName("정상적인_경우")
    void 정상적인_경우() {

        ParameterBag parameterBag = ParameterBagParser.parse("param1=paramValue1&param2=paramValue2");
        assertThat(parameterBag.getParameter("param1")).isEqualTo("paramValue1");
        assertThat(parameterBag.getParameter("param2")).isEqualTo("paramValue2");
    }

    @Test
    @DisplayName("param3 에 값이 비어 있을 때")
    void 세번째_파라미터_값_없음() {
        ParameterBag parameterBag = ParameterBagParser.parse("param1=paramValue1&param2=paramValue2&param3=");
        assertThat(parameterBag.getParameter("param1")).isEqualTo("paramValue1");
        assertThat(parameterBag.getParameter("param2")).isEqualTo("paramValue2");
        assertThat(parameterBag.getParameter("param3")).isEqualTo("");
    }


    @Test
    @DisplayName("Param3 지정이 잘못되어 있을 때")
    void 세번째_파라미터_값_지정_안됨() {
        ParameterBag parameterBag = ParameterBagParser.parse("param1=paramValue1&param2=paramValue2&param3");
        assertThat(parameterBag.getParameter("param1")).isEqualTo("paramValue1");
        assertThat(parameterBag.getParameter("param2")).isEqualTo("paramValue2");
        assertThat(parameterBag.getParameter("param3")).isEqualTo("");
    }


}