package view;

public class ViewTemplateCompileFailedException extends RuntimeException {
    private static final String MESSAGE = "View 템플릿 컴파일에 실패했습니다";

    public ViewTemplateCompileFailedException() {
        super(MESSAGE);
    }
}
