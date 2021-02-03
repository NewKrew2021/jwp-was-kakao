package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        //TODO : 지워야 할것
        if(true){
            DataBase.addUser(new User(
                    "dy2000",
                    "1234",
                    "김대영",
                    "15bass@naver.com"
            ));
            DataBase.addUser(new User(
                    "dy2001",
                    "4321",
                    "영대김",
                    "bass15@naver.com"
            ));
            DataBase.addUser(new User(
                    "coeat",
                    "taeoc",
                    "김태정",
                    "coeat.k@kakaocorp.com"
            ));
        }

        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection));
                thread.start();
            }
        }
    }
}
