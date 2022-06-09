package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IpJsonExtractor {
	
	public static String[] extractCityAndCountry() {
		JSONParser parser = new JSONParser();
        org.json.simple.JSONObject json = null;
        try {
            String jsonRespBlock = parseJsonResponse();
            json = (org.json.simple.JSONObject) parser.parse(jsonRespBlock);
        } catch (ParseException e) {
            e.getMessage();
        } catch (IOException e) {
        	e.getMessage();
		}
        Object city = json.get("city");
        Object country = json.get("country");

        return new String[] {city.toString(), country.toString()};
	}
	
	private static String parseJsonResponse() throws IOException {
		//API URL
		String sURL = "http://ip-api.com/json/".concat(getIpAddress());

        //GET response from API
        URL obj = new URL(sURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String sResponse = response.toString();
        System.out.println("JSON response from server: " + response.toString());

        return sResponse;
	  }

	private static String getIpAddress() {
		URL ipSource;
		BufferedReader in;
		String ip = null;
		try {
			ipSource = new URL("http://checkip.amazonaws.com");
			in = new BufferedReader(new InputStreamReader(
					ipSource.openStream()));
			ip = in.readLine();
		} catch (MalformedURLException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		return ip;
	}
}
