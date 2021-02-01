package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Dispatcher {
    private static final Dispatcher instance = new Dispatcher();
    private static final Map<String, Function<Request, byte[]>> dispatcher = new HashMap<>();

    private Dispatcher() {

    }

    public static void register(String urlPath, String method, Function<Request, byte[]> function) {
        dispatcher.put(makeKey(urlPath, method), function);
    }

    public static Dispatcher getInstance() {
        return instance;
    }

    public static byte[] run(Request request) {
        return dispatcher
                .get(makeKey(request.getUrlPath(), request.getMethod()))
                .apply(request);
    }

    private static String makeKey(String urlPath, String method) {
        return urlPath + ":" + method;
    }
}
