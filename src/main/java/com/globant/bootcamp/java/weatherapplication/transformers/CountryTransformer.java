package com.globant.bootcamp.java.weatherapplication.transformers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.bootcamp.java.weatherapplication.builders.CountryBuilder;
import com.globant.bootcamp.java.weatherapplication.model.Country;

public class CountryTransformer {
	
	public static List<Country> countryJSONtoCountryArray (String countryJson) throws IOException {
		List <Country> countries = new ArrayList<Country>();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(countryJson);
		JsonNode response = actualObj.get("RestResponse");
		JsonNode result = response.get("result");
		
		for (int i=0; i<result.size();i++) {
			Country c = new CountryBuilder()
					.setFullName(result.get(i).get("name").asText())
					.setAlpha2Code(result.get(i).get("alpha2_code").asText())
					.setAlpha3Code(result.get(i).get("alpha3_code").asText())
					.build();
			countries.add(c);
		}
	
		return countries;
	}
	
	public static Country countryJsonToCountryObj(String countryJson) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(countryJson);
		JsonNode response = actualObj.get("RestResponse");
		JsonNode result = response.get("result");
		
		Country c = new CountryBuilder()
				.setFullName(result.get("name").asText())
				.setAlpha2Code(result.get("alpha2_code").asText())
				.setAlpha3Code(result.get("alpha3_code").asText())
				.build();
		
		return c;
	}
	
	
	
}
