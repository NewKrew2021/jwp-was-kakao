package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParserTest {

    QueryParser queryParser;

    @BeforeEach
    void setUp(){
        queryParser = new QueryParser();
    }

    @DisplayName("query 가 null 이거나 빈문자열이면 빈배열을 돌려준다")
    @ParameterizedTest
    @NullAndEmptySource
    void nullAndEmpty(String query){
        assertThat(queryParser.parse(query)).hasSize(0);
    }

    @DisplayName("query 를 HttpRequestParam 으로 분리할 수 있다")
    @Test
    void parse(){
        List<HttpRequestParam> params = queryParser.parse("userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net");

        assertThat(params)
                .hasSize(4)
                .containsExactly(
                        new HttpRequestParam("userId", "javajigi"),
                        new HttpRequestParam("password", "password"),
                        new HttpRequestParam("name", "박재성"),
                        new HttpRequestParam("email", "javajigi@slipp.net")
                );
    }


}