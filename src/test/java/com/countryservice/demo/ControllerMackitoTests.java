package com.countryservice.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = { ControllerMackitoTests.class })
public class ControllerMackitoTests {

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countryController;

	List<Country> mycountries;
	Country country;

	@Test
	@Order(1)
	public void test_getAllCountries() {

		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "USA", "Washington"));

		when(countryService.getAllCountries()).thenReturn(mycountries); // Mocking
		ResponseEntity<List<Country>> res = countryController.getCountries();

		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(2, res.getBody().size());
	}

	@Test
	@Order(2)
	public void test_getCountryById() {

		country = new Country(2, "USA", "Washington");
		int countryId = 2;

		when(countryService.getCountryById(countryId)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryById(countryId);

		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(countryId, res.getBody().getId());

	}

	@Test
	@Order(3)
	public void test_getCountryByName() {

		country = new Country(2, "USA", "Washington");
		String countryName = "USA";

		when(countryService.getCountryByName(countryName)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryByName(countryName);

		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(countryName, res.getBody().getCountryName());

	}

	@Test
	@Order(4)
	public void test_addCountry() {

		country = new Country(3, "Germany", "Washington");

		when(countryService.addCountry(country)).thenReturn(country);
		ResponseEntity<Country> res = countryController.addCountry(country);

		assertEquals(HttpStatus.CREATED, res.getStatusCode());

		assertEquals(country, res.getBody());

	}

	@Test
	@Order(4)
	public void test_updateCountry() {

		country = new Country(3, "Japan", "Tokyo");
		int countryId = 3;

		when(countryService.getCountryById(countryId)).thenReturn(country);
		when(countryService.updateCountry(country)).thenReturn(country);

		ResponseEntity<Country> res = countryController.updateCountry(countryId, country);

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(country.getCountryName(), res.getBody().getCountryName());
		assertEquals(country.getId(), res.getBody().getId());
		assertEquals(country.getCountryCapital(), res.getBody().getCountryCapital());

	}

	@Test
	@Order(5)
	public void test_deleteCountry() {

		country = new Country(3, "Japan", "Tokyo");
		int countryId = 3;

		when(countryService.getCountryById(countryId)).thenReturn(country);
		ResponseEntity<Country> res = countryController.deleteCountry(countryId);

		assertEquals(HttpStatus.OK, res.getStatusCode());

	}

}
