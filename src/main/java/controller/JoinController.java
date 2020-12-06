package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import domain.HttpStatus;
import service.MemberService;

public class JoinController implements Controller {
	private static final MemberService memberService = new MemberService();

	@Override
	public void execute(HttpRequest request, HttpResponse response) {
		memberService.joinMember(request.getParameter());
		response.setHttpStatus(HttpStatus.FOUND);
		response.addHeader("Set-Cookie", "logined=false");
		response.sendRedirect("/index.html");
	}
}
