package com.countryservice.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTests {

	@Test
	@Order(1)
	void getAllCountriesIntegrationTest() throws JSONException {

		String expected = "[\r\n" + "    {\r\n" + "        \"id\": 1,\r\n" + "        \"countryName\": \"India\",\r\n"
				+ "        \"countryCapital\": \"Delhi\"\r\n" + "    },\r\n" + "    {\r\n" + "        \"id\": 2,\r\n"
				+ "        \"countryName\": \"USA\",\r\n" + "\"countryCapital\": \"Washington\"\r\n" + "    }\r\n"
				+ "]";

		TestRestTemplate restTemplate = new TestRestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getcountries", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	@Test
	@Order(2)
	void getCountryByIdIntegrationTest() throws JSONException {

		String expected = "{\"id\":1,\"countryName\":\"India\",\"countryCapital\":\"Delhi\"}";

		TestRestTemplate restTemplate = new TestRestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getcountries/1",
				String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	@Order(3)
	void getCountryByNameIntegrationTest() throws JSONException {

		String expected = "{\"id\":1,\"countryName\":\"India\",\"countryCapital\":\"Delhi\"}";

		TestRestTemplate restTemplate = new TestRestTemplate();

		ResponseEntity<String> response = restTemplate
				.getForEntity("http://localhost:8080/getcountries/countryname?name=India", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	@Order(4)
	void addCountryIntegrationTest() throws JSONException {

//		String payload = "{\r\n" + "    \"countryName\": \"Russia\",\r\n" + "    \"countryCapital\": \"Moscow\"\r\n"
//				+ "}";

		String expected = "{\r\n" + "    \"id\": 3,\r\n" + "    \"countryName\": \"Russia\",\r\n"
				+ "    \"countryCapital\": \"Moscow\"\r\n" + "}";

		Country country = new Country(3, "Russia", "Moscow");

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/addcountry", request,
				String.class);

		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Test
	@Order(5)
	void updateCountryIntegrationTest() throws JSONException {

//		String payload = "{\r\n" + "    \"countryName\": \"Russia\",\r\n" + "    \"countryCapital\": \"Moscow\"\r\n"
//				+ "}";

		String expected = "{\r\n" + "    \"id\": 3,\r\n" + "    \"countryName\": \"Italy\",\r\n"
				+ "    \"countryCapital\": \"Rome\"\r\n" + "}";

		Country country = new Country(3, "Italy", "Rome");

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/updatecountry/3", HttpMethod.PUT,
				request, String.class);

		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	@Test
	@Order(6)
	void deleteCountryIntegrationTest() throws JSONException {

//		String payload = "{\r\n" + "    \"countryName\": \"Russia\",\r\n" + "    \"countryCapital\": \"Moscow\"\r\n"
//				+ "}";

		String expected = "{\r\n" + "    \"id\": 3,\r\n" + "    \"countryName\": \"Italy\",\r\n"
				+ "    \"countryCapital\": \"Rome\"\r\n" + "}";

		Country country = new Country(3, "Italy", "Rome");

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/deletecountry/3",
				HttpMethod.DELETE, request, String.class);

		System.out.println(response.getStatusCode());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);

		//restTemplate.delete("http://localhost:8080/deletecountry/3");

	}

}
