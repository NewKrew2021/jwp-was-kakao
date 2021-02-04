package exceptions;

public class TemplateApplyFailException extends RuntimeException {
    private static final String TEMPLATE_APPLY_FAIL_MESSAGE = "템플릿 적용에 실패하였습니다.";

    public TemplateApplyFailException() {
        super(TEMPLATE_APPLY_FAIL_MESSAGE);
    }

}
