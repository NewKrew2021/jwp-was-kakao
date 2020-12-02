package domain;

public enum HttpStatus {
	STATIS_200(200, "OK"),
	STATUS_302(302, "Found"),
	STATUS_400(400, "Bad Request"),
	STATUS_401(401, "Unauthorized"),
	STATUS_404(404, "Not Found");

	private final int status;
	private final String message;

	HttpStatus(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
