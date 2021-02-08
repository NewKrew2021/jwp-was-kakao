package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ParametersTest {
    @DisplayName("단일 parameter 삽입")
    @Test
    void testSaveOneParameter() {
        Parameters parameters = new Parameters();
        parameters.saveParameter("hi=123");

        assertThat(parameters.get("hi")).isEqualTo("123");
    }

    @DisplayName("여러 parameter 삽입")
    @Test
    void testSaveParameters() {
        Parameters parameters = new Parameters();
        parameters.saveParameters(new String[]{"hi=123", "id=testId", "name=testName"});

        assertThat(Arrays.asList(
                parameters.get("hi"),
                parameters.get("id"),
                parameters.get("name"))).contains("123", "testId", "testName");
    }

    @DisplayName("존재하지 않는 parameter 는 empty 를 반환한다")
    @Test
    void testNonExistingParameter() {
        Parameters parameters = new Parameters();

        assertThat(parameters.get("hi")).isEmpty();
    }

    @DisplayName("value 가 존재하지 않아 삽입되지 않은 parameter 도 empty 를 반환한다")
    @Test
    void testInvalidValue() {
        Parameters parameters = new Parameters();
        parameters.saveParameter("hi=");

        assertThat(parameters.get("hi")).isEmpty();
    }
}