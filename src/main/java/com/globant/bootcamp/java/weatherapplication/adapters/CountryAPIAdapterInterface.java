package com.globant.bootcamp.java.weatherapplication.adapters;

import java.util.List;

import com.globant.bootcamp.java.weatherapplication.model.Country;

public interface CountryAPIAdapterInterface {
	
	public List<Country> getAllCountries();
	public Country getCountryShortCode(String alpha2); 
	public Country getCountryLongCode(String alpha3);
}
