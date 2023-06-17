package suite.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import payment.PaymentProcessorOkHttp;
import payment.TransactionProcessorOkHttp;

public class PaymentProcessorOkHttpTest {
	
	private int statusCode;
	private OkHttpClient client;
	private String name = "Ivan Ivanov";
	
	@Before
	public void setup() throws IOException {
		client = PaymentProcessorOkHttpTest.mockHttpClient("{\"key\": \"val\"}");
		statusCode = client.newCall(Mockito.any()).execute().code();
		TransactionProcessorOkHttp.createTransaction("\"\\\"" + name.trim() + "\\\"\"");
	}
	
	@Test
	public void testApiResponseStatusCodeForTen() throws IOException {
		// When
		PaymentProcessorOkHttp.processPaymentForTen("\"\\\"" + name.trim() + "\\\"\"");
		
		// Then
		assertEquals(statusCode, PaymentProcessorOkHttp.jsonRespCode);
	}
	
	@Test
	public void testApiResponseStatusCodeForTwenty() throws IOException {
		// When
		PaymentProcessorOkHttp.processPaymentForTwenty("\"\\\"" + name.trim() + "\\\"\"");
		
		// Then
		assertEquals(statusCode, PaymentProcessorOkHttp.jsonRespCode);
	}
	
	@Test
	public void testApiResponseStatusCodeForFifty() throws IOException {
		// When
		PaymentProcessorOkHttp.processPaymentForFifty("\"\\\"" + name.trim() + "\\\"\"");
		
		// Then
		assertEquals(statusCode, PaymentProcessorOkHttp.jsonRespCode);
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
                .code(200)
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
