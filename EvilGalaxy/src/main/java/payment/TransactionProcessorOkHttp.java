package payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

	public static void main(String[] args) throws IOException {
		createTransaction("\"4111111111111111\"", "\"Konstantin Drenski\"", "\"11\"", "\"2024\"", "\"344\"");
	}

	public static String createTransaction(String cardNumber, String fullName, String month, String year, String cvv) throws IOException {

		OkHttpClient client = new OkHttpClient();

		String auth = "ZQybpLRQmgVQrSD3CdAzB1QWM1E" + ":"
				+ "u3rqjGHzvFOLBmf4iT1q8xklPF4AmjFnd0X7PtylQPTKg7hXaCRTzPKdCWYhfCpf";
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

		String authHeaderValue = "Basic " + new String(encodedAuth);

		String data = "{\r\n" + "  \"environment_key\": \"ZQybpLRQmgVQrSD3CdAzB1QWM1E\",\r\n"
				+ "  \"payment_method\": {\r\n" + "    \"credit_card\": {\r\n" + "\"number\":" + cardNumber + ",\r\n"
				+ "\"verification_value\":" + cvv + ",\r\n" + "\"full_name\":" + fullName + ",\r\n" + "\"month\":"
				+ month + ",\r\n" + "\"year\":" + year + "\r\n" + "    }\r\n" + "  }\r\n" + "}";

		final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		RequestBody body = RequestBody.create(JSON, data);
		
		Request request = new Request.Builder()
				.url("https://core.spreedly.com/v1/payment_methods/restricted.json")
				.header("Authorization", authHeaderValue).post(body).build();
		
		Response response = client.newCall(request).execute();

		System.out.println(response.body().string());

		return response.body().string();
	}

	// for reporting purposes
	public static String[] extractTransactionData(String cardNumber, String fullName, String month, String year,
			String cvv) throws IOException {
		JSONParser parser = new JSONParser();
		org.json.simple.JSONObject json = null;
		try {
			String jsonRespBlock = createTransaction(cardNumber, fullName, month, year, cvv);
			json = (org.json.simple.JSONObject) parser.parse(jsonRespBlock);
		} catch (ParseException e) {
			e.getMessage();
		}

		if (json != null) {
			Object fulllName = ((HashMap) ((HashMap) json.get("transaction")).get("payment_method")).get("full_name");
			Object paymentMethodType = ((HashMap) ((HashMap) json.get("transaction")).get("payment_method"))
					.get("payment_method_type");
			Object issuerNumber = ((HashMap) ((HashMap) json.get("transaction")).get("payment_method"))
					.get("issuer_identification_number");
			return new String[] { fulllName.toString(), paymentMethodType.toString(), issuerNumber.toString() };
		}

		return new String[] { "NA", "NA", "NA" };
	}
}
