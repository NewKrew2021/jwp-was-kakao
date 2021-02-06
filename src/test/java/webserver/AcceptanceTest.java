package webserver;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

class WebServerThread implements Runnable {
    private static final int MAX_NUMBER_OF_TRY_COUNTS = 3;
    private static final int PORT_BOUNDS = 1 << 16;
    private static final int WELL_KNOWN_PORTS_COUNT = 1 << 10;
    public static int PORT = new Random().nextInt(PORT_BOUNDS - WELL_KNOWN_PORTS_COUNT) + WELL_KNOWN_PORTS_COUNT;
    private boolean created = false;

    @Override
    public void run() {
        int tryCount = 0;

        while (tryCount < MAX_NUMBER_OF_TRY_COUNTS) {
            try {
                WebServer.main(new String[]{Integer.toString(PORT)});
                created = true;
            } catch (Exception ignored) {
                PORT = new Random().nextInt(PORT_BOUNDS - WELL_KNOWN_PORTS_COUNT) + WELL_KNOWN_PORTS_COUNT;
                tryCount++;
            }
        }

        if (!created) {
            throw new RuntimeException("Could not instantiate web server");
        }
    }
}

public class AcceptanceTest {
    private static Thread webServer;

    @BeforeAll
    static void beforeAll() {
        webServer = new Thread(new WebServerThread());
        webServer.start();
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = WebServerThread.PORT;
    }

    @AfterAll
    static void afterAll() {
        webServer.interrupt();
    }
}
