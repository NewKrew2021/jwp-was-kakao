package response;

public class Response404Status extends AbstractResponseStatus {

    public Response404Status() {
        this.MESSAGE = "HTTP/1.1 404 Not Found \r\n";
    }
}
