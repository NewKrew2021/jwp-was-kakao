package view;

public class ViewResourcedLoadFailedException extends RuntimeException {
    private static final String MESSAGE = "View에 필요한 리로스를 로드하는데 오류가 발생했습니다.";

    public ViewResourcedLoadFailedException() {
        super(MESSAGE);
    }
}
