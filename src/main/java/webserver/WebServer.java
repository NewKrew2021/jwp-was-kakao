package webserver;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.RequestMapping;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = DEFAULT_PORT;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        List<? extends RequestMapping> requestMappings = getAllRequestMappings();

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection, requestMappings));
                thread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not run server\n" + e.getMessage());
        }
    }

    private static List<? extends RequestMapping> getAllRequestMappings() {
        List<Reflections> reflections = Arrays.asList(
                new Reflections("webserver"),
                new Reflections("user")
        );


        return reflections.stream()
                .flatMap(reflection -> reflection.getSubTypesOf(RequestMapping.class).stream())
                .map(mappingClass -> {
                    try {
                        return mappingClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Could not create RequestMappings\n" + e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }
}
