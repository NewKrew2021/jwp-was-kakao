package service;

import db.DataBase;
import domain.HttpRequestHeader;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpRequstParser;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemberServiceTest {
	private MemberService underTest = new MemberService();
	private HttpRequstParser httpRequstParser;
	private List<HttpRequestHeader> headers;

	@Test
	@DisplayName("회원가입요청인지 확인한다.")
	public void isJoinMemberTest() {
		joinMemberRequestData();
		assertTrue(underTest.isMemberJoinRequst(httpRequstParser));
	}

	@Test
	@DisplayName("회원가입시 유저 정보를 저장한다")
	public void joinMemberTest() {
		joinMemberRequestData();
		underTest.joinMember(getMemberInfo(httpRequstParser, headers));
		User user = DataBase.findUserById("adeldel");
		assertThat(user.getName()).isEqualTo("adeldel");
		assertThat(user.getPassword()).isEqualTo("password");
	}

	@Test
	@DisplayName("로그인 요청인지 확인한다.")
	public void memberLoginRequestTest() {
		loginRequstData("password");
		assertTrue(underTest.memberLoginRequest(httpRequstParser));
	}

	@Test
	@DisplayName("로그인시 유저정보를 확인한다.")
	public void loginRequestTest() {
		joinMemberRequestData();
		underTest.joinMember(getMemberInfo(httpRequstParser, headers));

		loginRequstData("password");
		assertTrue(underTest.memberLogin(getMemberInfo(httpRequstParser, headers)));

		loginRequstData("wrong_password");
		assertFalse(underTest.memberLogin(getMemberInfo(httpRequstParser, headers)));
	}

	private void joinMemberRequestData() {
		Reader reader = new StringReader("POST /user/create HTTP/1.1\n" +
				"Host: localhost:8080\n" +
				"Connection: keep-alive\n" +
				"Content-Length: 67\n" +
				"Content-Type: application/x-www-form-urlencoded\n" +
				"Accept: */*\n\n" +
				"userId=adeldel&password=password&name=adeldel&email=adel%40daum.net");

		httpRequstParser = new HttpRequstParser(new BufferedReader(reader));
		headers = httpRequstParser.getRequestHeaders();
	}

	private void loginRequstData(String password) {
		Reader reader = new StringReader("POST /user/login HTTP/1.1\n" +
				"Host: localhost:8080\n" +
				"Connection: keep-alive\n" +
				"Content-Length: 32\n" +
				"Content-Type: application/x-www-form-urlencoded\n" +
				"Accept: */*\n\n" +
				"userId=adeldel&password=" + password);

		httpRequstParser = new HttpRequstParser(new BufferedReader(reader));
		headers = httpRequstParser.getRequestHeaders();
	}

	private Map<String, String> getMemberInfo(HttpRequstParser requstParser, List<HttpRequestHeader> headers) {
		String requestBody = requstParser.getRequestBody(headers);
		return requstParser.getRequstParameters(requestBody);
	}
}
