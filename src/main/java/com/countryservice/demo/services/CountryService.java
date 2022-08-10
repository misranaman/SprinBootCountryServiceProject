package com.countryservice.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.AddResponse;
import com.countryservice.demo.repositories.CountryRepository;

@Component
@Service
public class CountryService {

	@Autowired
	CountryRepository countryrep;

	public List<Country> getAllCountries() {

		return countryrep.findAll();

	}

	public Country getCountryById(int id) {

		List<Country> countries = countryrep.findAll();

		Country country = null;

		for (Country con : countries) {

			if (con.getId() == id)
				country = con;
		}

		return country;

	}

	public Country getCountryByName(String countryName) {

		List<Country> countries = countryrep.findAll();

		Country country = null;

		for (Country con : countries) {

			if (con.getCountryName().equalsIgnoreCase(countryName))
				country = con;
		}

		return country;

	}

	public Country addCountry(Country country) {

		country.setId(getMaxId());
		countryrep.save(country);

		return country;

	}

	// Utility to get max id
	public int getMaxId() {

		return countryrep.findAll().size() + 1;

	}

	public Country updateCountry(Country country) {

		countryrep.save(country);
		return country;

	}

	public AddResponse deleteCountry(int id) {

		countryrep.deleteById(id);
		AddResponse res = new AddResponse();

		res.setMessage("Country Deleted.");
		res.setId(id);

		return res;

	}

	public void deleteCountry(Country country) {

		countryrep.delete(country);

	}

}
