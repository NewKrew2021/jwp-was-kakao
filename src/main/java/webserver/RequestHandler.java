package webserver;

import java.io.*;
import java.net.Socket;

import dto.HttpRequest;
import dto.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String lines = "";
            String line;

            while(!(line = br.readLine()).equals("")){
                lines += line + "\n";
            }

            HttpRequest request = new HttpRequest(lines);
            if(request.getContentLength() != 0) {
                request.setBody(IOUtils.readData(br, request.getContentLength()));
            }

            HttpResponse response = DispatcherServlet.run(request);

            DataOutputStream dos = new DataOutputStream(out);
            writeHttpResponse(dos, response);

        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {

        }
    }

    private void writeHttpResponse(DataOutputStream dos, HttpResponse response){
        try{
            dos.writeBytes(response.getHeaders());
            dos.write(response.getBody(), 0, response.getBody().length);
            dos.flush();
        }catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
