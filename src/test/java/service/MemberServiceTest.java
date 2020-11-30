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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemberServiceTest {
	private MemberService underTest;
	private HttpRequstParser httpRequstParser;
	private List<HttpRequestHeader> headers;

	@BeforeEach
	public void before() {
		underTest = new MemberService();
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

	@Test
	@DisplayName("회원가입요청인지 확인한다.")
	public void isJoinMemberTest() {
		assertTrue(underTest.isMemberJoinRequst(httpRequstParser));
	}

	@Test
	@DisplayName("회원가입시 유저 정보를 저장한다")
	public void joinMemberTest() {
		underTest.joinMember(httpRequstParser, headers);
		User user = DataBase.findUserById("adeldel");
		assertThat(user.getName()).isEqualTo("adeldel");
		assertThat(user.getPassword()).isEqualTo("password");
	}
}
