package com.globant.bootcamp.java.weatherapplication.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.proxies.CountryProxy;


@RestController
@RequestMapping("/country/")
public class CountryController {
	
	@Autowired
	private CountryProxy cp;
	
	ObjectMapper mapper = new ObjectMapper();
	
	//*********************************************************************************************/
	//********************************* GET ALL COUNTRIES *****************************************/
	//*********************************************************************************************/
	
	@RequestMapping(value = "/get/all", method = RequestMethod.GET, produces ="application/json")
	public ResponseEntity<String> getAllCountries() {   	
		
		String result = cp.getAllCountries();
		if(result=="There are no registers available") {
			return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	//*********************************************************************************************/
	//********************************* GET COUNTRY BY SHORT CODE *********************************/
	//*********************************************************************************************/
	
	@RequestMapping(value = "/get/2code/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> getCountryByShortCode(@RequestParam(value="2code", required=true) String alpha2) {   	
		
		String result = cp.getCountryByShortName2(alpha2);
		if(result.equals("The Country doesnt exist in our registers")) {
			return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
		}
		else {
			if(result.equals("The country code must be of length 2 and cannot contain numbers")) {
				return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	//*********************************************************************************************/
	//********************************* GET COUNTRY BY LONG CODE **********************************/
	//*********************************************************************************************/
	
	@RequestMapping(value = "/get/3code/", method = RequestMethod.GET, produces ="application/json")
	public ResponseEntity<String> getCountryByLongCode(@RequestParam(value="3code", required=true) String alpha3) {   	
		
		String result = cp.getCountryByShortName3(alpha3);
		if(result.equals("The Country doesnt exist in our registers")) {
			return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
		}
		else {
			if(result.equals("The country code must be of length 3 and cannot contain numbers")) {
				return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	//*********************************************************************************************/
	//********************************* INSERT COUNTRY INTO DB ************************************/
	//*********************************************************************************************/
	
	/*save country first you have to find a country json from the client or insert a new one by
	 * yourself, then send the json string to the method*/
	@RequestMapping(value = "/new/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> newCountry(@RequestBody String country) throws IOException {   	
		
		Country c = mapper.readValue(country, Country.class);
		
		String result = cp.insertCountry(c);
		if(result.equals("The country has been inserted in the DB")) {
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
		}
	}
	
}
