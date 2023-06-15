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

public class TransactionProcessor {

	public static void main(String[] args) {
		createTransaction("\"4111111111111111\"", "\"Konstantin Drenski\"", "\"11\"", "\"2024\"", "\"344\"");
	}

	public static String createTransaction(String cardNumber, String fullName, 
			String month, String year, String cvv) {
		
		StringBuilder response = new StringBuilder();

		try {
			URL url = new URL("https://core.spreedly.com/v1/payment_methods/restricted.json");

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
					"  \"environment_key\": \"ZQybpLRQmgVQrSD3CdAzB1QWM1E\",\r\n" + 
					"  \"payment_method\": {\r\n" + 
					"    \"credit_card\": {\r\n" + 
					"\"number\":" + cardNumber + ",\r\n" + 
					"\"verification_value\":" + cvv + ",\r\n" + 
					"\"full_name\":" + fullName + ",\r\n" + 
					"\"month\":" + month + ",\r\n" +
					"\"year\":" + year + "\r\n" + 
					"    }\r\n" + 
					"  }\r\n" + 
					"}";

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = httpURLConnection.getOutputStream();
			stream.write(out);

			System.out.println(httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage());

			try (BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
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
	
	// for reporting purposes
	public static String[] extractTransactionData(String cardNumber, String fullName, 
			String month, String year, String cvv) {
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
			Object paymentMethodType = ((HashMap) ((HashMap) json.get("transaction")).get("payment_method")).get("payment_method_type");
			Object issuerNumber = ((HashMap) ((HashMap) json.get("transaction")).get("payment_method")).get("issuer_identification_number");
			return new String[] { fulllName.toString(), paymentMethodType.toString(), issuerNumber.toString() };			
		}
		
		return new String[] {"NA", "NA", "NA"};
	}
}
