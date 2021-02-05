package view;

import webserver.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OutputView {
    private final OutputStream outputStream;

    private OutputView(OutputStream out) {
        this.outputStream = new DataOutputStream(out);
    }

    public static OutputView from(OutputStream out) {
        return new OutputView(out);
    }

    public void write(HttpResponse response) throws IOException {
        String header = response.getHeader();
        byte[] body = response.getBody();

        outputStream.write(header.getBytes());
        outputStream.write(body, 0, body.length);
        outputStream.flush();
    }
}
