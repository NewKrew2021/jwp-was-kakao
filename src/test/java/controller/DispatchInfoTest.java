package controller;

import annotation.web.RequestMethod;
import http.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DispatchInfoTest {
    @DisplayName("Request를 처리할 적절한 Handler를 리턴하는지 테스")
    @Test
    void testFindHandler() {
        HttpRequest userCreateRequest = new HttpRequest(RequestMethod.POST, "/user/create");
        assertThat(DispatchInfo.UserCreate.getRequestHandler()).isEqualTo(DispatchInfo.findMatchingHandler(userCreateRequest));

        HttpRequest userListRequest = new HttpRequest(RequestMethod.GET, "/user/list");
        assertThat(DispatchInfo.UserList.getRequestHandler()).isEqualTo(DispatchInfo.findMatchingHandler(userListRequest));

        HttpRequest faviconRequest = new HttpRequest(RequestMethod.GET, "/favicon.ico");
        assertThat(DispatchInfo.Favicon.getRequestHandler()).isEqualTo(DispatchInfo.findMatchingHandler(faviconRequest));

    }
}