package response;

public class Response302Status extends AbstractResponseStatus {

    public Response302Status() {
        this.MESSAGE = "HTTP/1.1 302 Found \r\n";
    }
}
