package suite.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import util.LocationJsonExtractor;

public class LocationJsonExtractorTest {

	private String[] cityAndCountry;

	@Before
	public void setUp() {
		cityAndCountry = LocationJsonExtractor.extractCityAndCountry();
	}

	@Test
	public void testApiResponseStatusCode() throws ClientProtocolException, IOException {
		// Given
		String ipAddress = "95.87.216.40";
		HttpUriRequest request = new HttpGet("http://ip-api.com/json/" + ipAddress);

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

		// Then
		assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	}

	@Test
	public void testCityFromJsonResp() throws Exception {
		String content = new String(Files.readAllBytes(Paths.get("testUtils/ipresp.json")));
		JSONObject jsonObject = new JSONObject(content);
		String expectedCity = jsonObject.getString("city");
		
		assertEquals(expectedCity, cityAndCountry[0]);
	}

	@Test
	public void testCountryFromJsonResp() throws Exception {
		String content = new String(Files.readAllBytes(Paths.get("testUtils/ipresp.json")));
		JSONObject jsonObject = new JSONObject(content);
		String expectedCountry = jsonObject.getString("country");
		
		assertEquals(expectedCountry, cityAndCountry[1]);
	}

}