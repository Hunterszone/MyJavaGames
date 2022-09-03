package suite.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

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
	public void testJsonCityResponse() throws JSONException {
		JSONObject actual = new JSONObject();
		actual.put("city", cityAndCountry[0]);
		
		JSONAssert.assertEquals("{city:" + cityAndCountry[0] + "}", actual, true);
	}

	@Test
	public void testJsonCountryResponse() throws JSONException {
		JSONObject actual = new JSONObject();
		actual.put("country", cityAndCountry[1]);
		
		JSONAssert.assertEquals("{country:" + cityAndCountry[1] + "}", actual, true);
	}

	@Test
	public void testThatCityAndCountryAreFetched() {
		assertNotEquals("No city and country found!", Arrays.toString(cityAndCountry), "[NA, NA]");
	}
}