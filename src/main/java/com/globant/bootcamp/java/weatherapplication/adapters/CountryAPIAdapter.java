package com.globant.bootcamp.java.weatherapplication.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.services.RestCountriesClient;
import com.globant.bootcamp.java.weatherapplication.transformers.CountryTransformer;

public class CountryAPIAdapter implements CountryAPIAdapterInterface {
	
	@Resource
	private RestCountriesClient RestCountriesClient;
	
	//*********************************************************************************************/
	//********************************* GET ALL COUNTRIES *****************************************/
	//*********************************************************************************************/
	
	@Override
	public List<Country> getAllCountries(){

		String response = RestCountriesClient.getAllCountries();	
		List<Country> countries = new ArrayList<Country>();
		try {
			countries = CountryTransformer.countryJSONtoCountryArray(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return countries;
	}
	
	//*********************************************************************************************/
	//********************************* GET COUNTRY BY SHORT CODE *********************************/
	//*********************************************************************************************/
	
	@Override
	public Country getCountryShortCode(String alpha2) {
		
		String response = RestCountriesClient.getCountryByShortName2(alpha2);
		Country c = new Country();
		try {
			c = CountryTransformer.countryJsonToCountryObj(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	
	//*********************************************************************************************/
	//********************************* GET COUNTRY BY LONG CODE **********************************/
	//*********************************************************************************************/
	
	@Override
	public Country getCountryLongCode(String alpha3) {
		
		String response = RestCountriesClient.getCountryByShortName3(alpha3);
		Country c = new Country();
		try {
			c = CountryTransformer.countryJsonToCountryObj(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	
}
