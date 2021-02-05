package application;

import domain.HttpRequest;
import domain.HttpResponse;
import service.MemberService;

public class CreateUserController extends AbstractController {

    public static final String INDEX_HTML = "/index.html";

    private final MemberService memberService;

    public CreateUserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (memberService.isInvalidMemberRequest(httpRequest)) {
            throw new IllegalArgumentException();
        }
        memberService.addUser(httpRequest);
        httpResponse.sendRedirect(INDEX_HTML);
    }
}
