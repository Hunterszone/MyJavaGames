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

public class PaymentProcessorOkHttp {
	public static void main(String[] args) throws IOException {
		processPayment("\"IvfxMCsSW8zjhVrsAnBGVGL9yfO\"", "\"233\"", "\"BGN\"");
	}

	public static String processPayment(String paymentMethToken, String amount, String currencyCode)
			throws IOException {

		OkHttpClient client = new OkHttpClient();

		String auth = "ZQybpLRQmgVQrSD3CdAzB1QWM1E" + ":"
				+ "u3rqjGHzvFOLBmf4iT1q8xklPF4AmjFnd0X7PtylQPTKg7hXaCRTzPKdCWYhfCpf";
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

		String authHeaderValue = "Basic " + new String(encodedAuth);

		String data = "{\r\n" + "        \"transaction\": {\r\n" + "\"payment_method_token\":" + paymentMethToken
				+ ",\r\n" + "\"amount\":" + amount + ",\r\n" + "\"currency_code\":" + currencyCode + ",\r\n"
				+ "          \"retain_on_success\": true\r\n" + "        }\r\n" + "      }";

		final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		RequestBody body = RequestBody.create(JSON, data);
		Request request = new Request.Builder()
				.url("https://core.spreedly.com/v1/gateways/RmWNf0NVLOuZqthwKgHgjua4Cxn/purchase.json")
				.header("Authorization", authHeaderValue).post(body).build();
		Response response = client.newCall(request).execute();
		
		System.out.println(response.body().string());
		
		return response.body().string();
	}

	public static String[] extractPaymentData(String paymentMethToken, String amount, String currencyCode)
			throws IOException {
		JSONParser parser = new JSONParser();
		org.json.simple.JSONObject json = null;
		try {
			String jsonRespBlock = processPayment(paymentMethToken, amount, currencyCode);
			json = (org.json.simple.JSONObject) parser.parse(jsonRespBlock);
		} catch (ParseException e) {
			e.getMessage();
		}

		if (json != null) {
			Object currency = ((HashMap) json.get("transaction")).get("currency_code");
			Object amountt = ((HashMap) json.get("transaction")).get("amount");
			return new String[] { currency.toString(), amountt.toString() };
		}

		return new String[] { "NA", "NA" };
	}
}
