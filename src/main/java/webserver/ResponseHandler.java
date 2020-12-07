package webserver;

import com.github.jknack.handlebars.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.TemplateUtils;
import webserver.http.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ResponseHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public void handleResponse(DataOutputStream dos, Response response) throws IOException {
        if (StringUtils.hasText(response.getViewName())) {
            setBody(response);
        }

        responseHeader(dos, response.getHeaders());
        responseBody(dos, response.getBody());
    }

    private void setBody(Response response) throws IOException {
        response.setBody(getTemplate(response).apply(response.getModel())
                                 .getBytes(UTF_8));
    }

    Template getTemplate(Response response) throws IOException {
        return TemplateUtils.getTemplate(response.getViewName());
    }

    private void responseHeader(DataOutputStream dos, List<String> headers) throws IOException {
        for (String header : headers) {
            logger.debug("responseHeader: {}", header);
            dos.writeBytes(header + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        if (body == null) {
            return;
        }
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
