package com.globant.bootcamp.java.weatherapplication.proxies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.bootcamp.java.weatherapplication.adapters.CountryAPIAdapter;
import com.globant.bootcamp.java.weatherapplication.builders.CountryBuilder;
import com.globant.bootcamp.java.weatherapplication.db.CountryDAO;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.services.RestCountriesClient;
import com.globant.bootcamp.java.weatherapplication.db.DBManager;

public class CountryProxy implements RestCountriesClient {
	
	@Autowired
	private CountryAPIAdapter ca;
	
	@Autowired
	private CountryDAO cd;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private DBManager db;
	
	//*********************************************************************************************/
	//********************************* GET ALL COUNTRIES *****************************************/
	//*********************************************************************************************/	
	
	@Override
	public String getAllCountries() {
		try	{
			List<Country> clientResponse = ca.getAllCountries();
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientResponse);
		}
		catch (Exception ex) {
			List<Country>daoResponse = cd.selectAll();
			try {
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(daoResponse);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return "There are no registers available";
	}

	//*********************************************************************************************/
	//********************************* GET COUNTRY BY SHORT CODE *********************************/
	//*********************************************************************************************/

	@Override
	public String getCountryByShortName2(String alpha2) {
		//Checks if the input is correct
		if(alpha2.length()!=2 || alpha2.matches(".*\\d+.*")) {
			return "The country code must be of length 2 and cannot contain numbers";
		}
		else {
			try {
				Country clientResponse = ca.getCountryShortCode(alpha2);
				//if the country doesnt exist in the db --> insert it.
				db.insertCountry(clientResponse);
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientResponse);
			}
			catch (Exception ex) {
				Country c = new CountryBuilder().setAlpha2Code(alpha2).build();
				Country daoResponse = cd.selectOneByShortCode(c);
				if(daoResponse == null) {
					return "The Country doesnt exist in our registers";
				}
				try {
					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(daoResponse);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		return "The Country doesnt exist in our registers";
	}

	//*********************************************************************************************/
	//********************************* GET COUNTRY BY LONG CODE **********************************/
	//*********************************************************************************************/

	@Override
	public String getCountryByShortName3(String alpha3) {
		if(alpha3.length()!=3 || alpha3.matches(".*\\d+.*")) {
			return "The country code must be of length 3 and cannot contain numbers";
		}
		else {		
			try {
				Country clientResponse = ca.getCountryLongCode(alpha3);
				db.insertCountry(clientResponse);
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientResponse);
			}
			catch (Exception ex) {
				Country c = new CountryBuilder().setAlpha3Code(alpha3).build();
				Country daoResponse = cd.selectOneByLongCode(c);
				if(daoResponse == null) {
					return "The Country doesnt exist in our registers";
				}
				try {
					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(daoResponse);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		return "The Country doesnt exist in our registers";
	}

	//*********************************************************************************************/
	//********************************* INSERT COUNTRY INTO DB ************************************/
	//*********************************************************************************************/

	public String insertCountry(Country c) {
		if(c.getAlpha2Code().isEmpty() || c.getAlpha3Code().isEmpty() || c.getFullName().isEmpty()) {
			return "Check that the fields aren't empty in the JSON being posted";
		}
		else {
			if (db.insertCountry(c)==true) {
				return "The country has been inserted in the DB";
			}
			return "The country couldnt be inserted";
		}
	}

}
