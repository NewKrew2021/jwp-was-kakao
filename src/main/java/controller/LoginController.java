package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import domain.HttpStatus;
import service.MemberService;

public class LoginController extends AbstractController {
	private static final MemberService memberService = new MemberService();

	@Override
	public void service(HttpRequest request, HttpResponse response) {
		boolean loginSuccess = memberService.memberLogin(request.getParameter());
		if (loginSuccess) {
			response.addHeader("Set-Cookie", "logined=true");
			response.sendRedirect("/index.html");
		}
	}

	@Override
	public void doPost(HttpRequest request, HttpResponse response) {
		response.setHttpStatus(HttpStatus.FOUND);
		service(request, response);
		response.addHeader("Set-Cookie", "logined=false");
		response.sendRedirect("/user/login_failed.html");
	}

	@Override
	public void doGet(HttpRequest request, HttpResponse response) {
		// Nothing
	}
}
