package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import domain.HttpStatus;
import service.MemberService;

public class JoinController extends AbstractController {
	private static final MemberService memberService = new MemberService();

	@Override
	public void service(HttpRequest request, HttpResponse response) {
		memberService.joinMember(request.getParameter());
		response.addHeader("Set-Cookie", "logined=false");
		response.sendRedirect("/index.html");
	}

	@Override
	public void doPost(HttpRequest request, HttpResponse response) {
		response.setHttpStatus(HttpStatus.FOUND);
		service(request, response);
	}

	@Override
	public void doGet(HttpRequest request, HttpResponse response) {
		// Nothing
	}
}
