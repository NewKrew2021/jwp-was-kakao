package response;

public class Response500Status extends AbstractResponseStatus {

    public Response500Status() {
        this.MESSAGE = "HTTP/1.1 500 Internal Server Error \r\n";
    }
}
