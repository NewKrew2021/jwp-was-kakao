package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import domain.HttpStatus;
import service.MemberService;

public class LoginController implements Controller {
	private static final MemberService memberService = new MemberService();

	@Override
	public void execute(HttpRequest request, HttpResponse response) {
		response.setHttpStatus(HttpStatus.FOUND);
		boolean loginSuccess = memberService.memberLogin(request.getParameter());
		if (!loginSuccess) {
			response.addHeader("Set-Cookie", "logined=false");
			response.sendRedirect("/user/login_failed.html");
		}
		response.addHeader("Set-Cookie", "logined=true");
		response.sendRedirect("/index.html");
	}
}
