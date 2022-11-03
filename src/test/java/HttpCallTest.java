import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpCallTest {

    private final String baseURL = "https://d6c64f3b-c453-479a-a8cb-da4049f27762.mock.pstmn.io";
    private final String contactPath = "/contacts";

    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void testGetRequest() throws IOException {
        Request request = new Request.Builder().url(baseURL + contactPath).build();

        Call call = client.newCall(request);
        Response response = call.execute();
        RealResponseBody responseBody = (RealResponseBody) response.body();
        assert responseBody != null;
        InputStreamReader isr = new InputStreamReader(responseBody.byteStream(),
                StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        br.lines().forEach(System.out::println);

        Assertions.assertEquals(response.code(), 200);

    }
}
