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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.bootcamp.java.weatherapplication.builders.CountryBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.StateBuilder;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.proxies.StateProxy;


@RestController
@RequestMapping("/state/")
public class StateController {

	@Autowired
	private StateProxy sp;

	
	//*********************************************************************************************/
	//*************************** GET ALL STATES FROM ONE COUNTRY *********************************/
	//*********************************************************************************************/

	@RequestMapping(value = "/get/all/", method = RequestMethod.GET, produces ="application/json")
	public ResponseEntity<String> getAllStates(@RequestParam(value="3code", required=true) String alpha3) throws IOException{   	

		String result = sp.getStateByLongCountryCode(alpha3);
		if(result.equals("There is no matching Country")) {
			return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	//*********************************************************************************************/
	//*** GET ONE STATE OF ONE COUNTRY USING THE STATE'S SHORT CODE AND COUNTRY'S LONG CODE *******/
	//*********************************************************************************************/



	@RequestMapping(value = "/get/one/", method = RequestMethod.GET, produces ="application/json")
	public ResponseEntity<String> getOneState(
			@RequestParam(value="country", required=true) String country,
			@RequestParam(value="state", required=true) String state
			) throws IOException{   	

		String result = sp.getStateByLongCountryCodeShortStateCode(country, state);
		if(result.equals("There is no matching record")) {
			return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}



	//*********************************************************************************************/
	//******************************************* INSERT STATE ************************************/
	//*********************************************************************************************/

	@RequestMapping(value = "/new/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> newState(@RequestBody String state) throws JsonParseException, JsonMappingException, IOException {   	


		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(state);

		State s = new StateBuilder()
				.setIdState(actualObj.get("idState").asInt())
				.setFullName(actualObj.get("fullName").asText())
				.setAlpha2Code(actualObj.get("alpha2Code").asText())
				.setLargestCity(actualObj.get("largestCity").asText())
				.setCapitalCity(actualObj.get("capitalCity").asText())
				.setArea(actualObj.get("area").asText()).build();

		Country c = new CountryBuilder().setFullName(actualObj.get("country").get("fullName").asText())
				.setAlpha2Code(actualObj.get("country").get("alpha2Code").asText())
				.setAlpha3Code(actualObj.get("country").get("alpha3Code").asText()).build();

		s.setCountry(c);

		String result = sp.insertState(s);
		if(result == "The state couldnt be inserted") {
			return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}


