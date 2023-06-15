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

public class PaymentProcessor {
	public static void main(String[] args) {
		processPayment("\"IzlC3SrTy7WYx170TblouuY85F5\"", "\"233\"", "\"BGN\"");
	}

	public static String processPayment(String paymentMethToken, String amount, 
			String currencyCode) {
		
		StringBuilder response = new StringBuilder();

		try {
			URL url = new URL("https://core.spreedly.com/v1/gateways/RmWNf0NVLOuZqthwKgHgjua4Cxn/purchase.json");

			String auth = "ZQybpLRQmgVQrSD3CdAzB1QWM1E" + ":"
					+ "u3rqjGHzvFOLBmf4iT1q8xklPF4AmjFnd0X7PtylQPTKg7hXaCRTzPKdCWYhfCpf";
			byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

			String authHeaderValue = "Basic " + new String(encodedAuth);

			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			httpURLConnection.setRequestProperty("Authorization", authHeaderValue);
			httpURLConnection.setRequestProperty("Accept","*/*");
			httpURLConnection.setDoOutput(true);

			String data = "{\r\n" + 
					"        \"transaction\": {\r\n" + 
					"\"payment_method_token\":" + paymentMethToken + ",\r\n" + 
					"\"amount\":" + amount + ",\r\n" + 
					"\"currency_code\":" + currencyCode + ",\r\n" +
					"          \"retain_on_success\": true\r\n" + 
					"        }\r\n" + 
					"      }";

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = httpURLConnection.getOutputStream();
			stream.write(out);

			System.out.println(httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage());

			try (BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				System.out.println(response.toString());
			}

			httpURLConnection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}
	
	public static String[] extractPaymentData(String paymentMethToken, String amount, 
			String currencyCode) {
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
		
		return new String[] {"NA", "NA"};
	}
}
