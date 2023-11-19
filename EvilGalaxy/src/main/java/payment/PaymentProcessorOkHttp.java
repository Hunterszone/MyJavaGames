package payment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PaymentProcessorOkHttp implements Payable {

	public static int jsonRespCode;

	@Override
	public String processPayment(String custName, double amount) throws IOException {
		String paymentMethToken = TransactionProcessorOkHttp.extractTransactionToken(custName);

		String payload = "{\r\n" + "        \"transaction\": {\r\n" + "\"payment_method_token\": " + paymentMethToken
				+ ",\r\n" + "          \"amount\": " + amount + ",\r\n" + "          \"currency_code\": \"USD\",\r\n"
				+ "          \"retain_on_success\": true\r\n" + "        }\r\n" + "      }";

		return processRequest(payload);
	}

	private static String processRequest(String data) throws IOException {
		OkHttpClient client = new OkHttpClient();

		String auth = "ZQybpLRQmgVQrSD3CdAzB1QWM1E" + ":"
				+ "u3rqjGHzvFOLBmf4iT1q8xklPF4AmjFnd0X7PtylQPTKg7hXaCRTzPKdCWYhfCpf";
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

		String authHeaderValue = "Basic " + new String(encodedAuth);

		final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		RequestBody body = RequestBody.create(JSON, data);
		Request request = new Request.Builder()
				.url("https://core.spreedly.com/v1/gateways/RmWNf0NVLOuZqthwKgHgjua4Cxn/purchase.json")
				.header("Authorization", authHeaderValue).post(body).build();
		Response response = client.newCall(request).execute();

		jsonRespCode = response.code();
		String jsonRespBody = response.body().string();

		System.out.println("Payment has been processesd with resp code: " + jsonRespCode);
		System.out.println("API response: " + jsonRespBody);

		return jsonRespBody;
	}
	
}
