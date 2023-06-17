package payment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TransactionProcessorOkHttp {

	public static String jsonRespBody;
	public static int jsonRespCode;

	public static String createTransaction(String custName) throws IOException {

		OkHttpClient client = new OkHttpClient();

		String auth = "ZQybpLRQmgVQrSD3CdAzB1QWM1E" + ":"
				+ "u3rqjGHzvFOLBmf4iT1q8xklPF4AmjFnd0X7PtylQPTKg7hXaCRTzPKdCWYhfCpf";
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

		String authHeaderValue = "Basic " + new String(encodedAuth);

		String payload = "{\r\n" + 
				"  \"environment_key\": \"ZQybpLRQmgVQrSD3CdAzB1QWM1E\",\r\n" + 
				"  \"payment_method\": {\r\n" + 
				"    \"credit_card\": {\r\n" + 
				"      \"number\": \"4111111111111111\",\r\n" + 
				"      \"verification_value\": \"344\",\r\n" + 
				"\"full_name\":" + custName + ",\r\n" + 
				"      \"month\": \"11\",\r\n" + 
				"      \"year\": \"2024\"\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}";

		final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		RequestBody body = RequestBody.create(JSON, payload);
		
		Request request = new Request.Builder()
				.url("https://core.spreedly.com/v1/payment_methods/restricted.json")
				.header("Authorization", authHeaderValue)
				.post(body)
				.build();
		
		Response response = client.newCall(request).execute();
		
		jsonRespBody = response.body().string();
		jsonRespCode = response.code();

		System.out.println("Transaction has been created with resp code: " + jsonRespCode);

		return jsonRespBody;
	}

	// for reporting purposes
	public static String extractTransactionToken(String custName) throws IOException {
		JSONParser parser = new JSONParser();
		org.json.simple.JSONObject json = null;
		try {
			json = (org.json.simple.JSONObject) parser.parse(jsonRespBody);
		} catch (ParseException e) {
			e.getMessage();
		}
		
		if (json != null) {
			Object paymentMethToken = ((HashMap) ((HashMap) json.get("transaction")).get("payment_method")).get("token");
			System.out.println("PM token: " + paymentMethToken);
			return "\"" + paymentMethToken + "\"";
		}

		return "NA";
	}
}
