package suite.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
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
		String expectedCity = cityAndCountry[0];
		
		assertNotEquals(expectedCity, "NA");
	}

	@Test
	public void testCountryFromJsonResp() throws Exception {
		String expectedCountry = cityAndCountry[1];
		
		assertNotEquals(expectedCountry, "NA");
	}

}