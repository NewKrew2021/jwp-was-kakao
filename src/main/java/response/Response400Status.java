package response;

public class Response400Status extends AbstractResponseStatus {

    public Response400Status() {
        this.MESSAGE = "HTTP/1.1 400 Bad Request \r\n";
    }

}
