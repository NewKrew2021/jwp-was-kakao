package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import domain.HttpStatus;
import exception.InvalidTemplateException;
import model.User;
import service.MemberService;
import template.HandlebarTemplateEngine;

import java.util.List;

public class MembersController extends AbstractController {
	private static final MemberService memberService = new MemberService();
	private static final HandlebarTemplateEngine handlebars = new HandlebarTemplateEngine();

	@Override
	public void service(HttpRequest request, HttpResponse response) {
		try {
			List<User> members = memberService.getAllMembers();
			response.forwardBody(handlebars.apply("/user/list", members).getBytes());
		} catch (InvalidTemplateException e) {
			throw e;
		}
	}

	@Override
	public void doPost(HttpRequest request, HttpResponse response) {
		// Nothing
	}

	@Override
	public void doGet(HttpRequest request, HttpResponse response) {
		if (isLoginCookie(request)) {
			response.setHttpStatus(HttpStatus.OK);
			response.setContentType(request.getContentType());
			service(request, response);
		}
		response.setHttpStatus(HttpStatus.FOUND);
		response.addHeader("Set-Cookie", "logined=false");
		response.sendRedirect("/user/login.html");
	}

	public boolean isLoginCookie(HttpRequest request) {
		return request.getCookies().keySet().stream()
				.filter(key -> "logined".equals(key)
						&& "true".equals(request.getCookies().get(key)))
				.findAny()
				.isPresent();
	}
}
