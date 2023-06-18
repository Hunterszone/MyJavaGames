package suite.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import payment.TransactionProcessorOkHttp;

public class TransactionProcessorOkHttpTest {
	
	@Test
	public void testApiResponseStatusCode() throws IOException {
		// Given	
		OkHttpClient client = TransactionProcessorOkHttpTest.mockHttpClient("{\"key\": \"val\"}");
		String name = "Ivan Ivanov";

		// When
		TransactionProcessorOkHttp.createTransaction("\"\\\"" + name.trim() + "\\\"\"");
		
		// Then
		int statusCode = client.newCall(Mockito.any(Request.class)).execute().code();
		assertEquals(statusCode, TransactionProcessorOkHttp.jsonRespCode);
	}
	
	private static OkHttpClient mockHttpClient(final String serializedBody) throws IOException {
        final OkHttpClient okHttpClient = Mockito.mock(OkHttpClient.class);

        final Call remoteCall = Mockito.mock(Call.class);

        final Response response = new Response.Builder()
                .request(new Request.Builder()
                		.url("https://some-url.com")
                		.build())
                .protocol(Protocol.HTTP_1_1)
                .message("")
                .code(201)
                .body(
                   ResponseBody.create(
                        MediaType.parse("application/json"),
                        serializedBody
                ))
                .build();

        Mockito.when(remoteCall.execute()).thenReturn(response);
        Mockito.when(okHttpClient.newCall(Mockito.any())).thenReturn(remoteCall);

        return okHttpClient;
    }
}
