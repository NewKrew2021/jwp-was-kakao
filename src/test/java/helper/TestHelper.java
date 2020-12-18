package helper;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class TestHelper {

    public static HttpClientBuilder testHttpClient() {
        return HttpClients.custom()
                .disableRedirectHandling()
                .disableAutomaticRetries();
    }
}
