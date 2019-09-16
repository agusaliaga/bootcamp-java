package com.globant.bootcamp.java.weatherapplication.transformers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.bootcamp.java.weatherapplication.builders.StateBuilder;
import com.globant.bootcamp.java.weatherapplication.model.State;



public class StateTransformer {
	
	
	
	
	public static List<State> stateJSONtoStateArray (String stateJson) throws IOException {
		List <State> states = new ArrayList<State>();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(stateJson);
		JsonNode response = actualObj.get("RestResponse");
		JsonNode result = response.get("result");
		
			
		for (int i=0; i<result.size();i++) {
		
			State s = new StateBuilder()
					.setCountry(null)
					.setIdState(result.get(i).get("id").asInt())
					.setFullName(result.get(i).get("name").asText())
					.setAlpha2Code(result.get(i).get("abbr").asText())
					.setLargestCity(result.get(i).get("largest_city").asText())
					.setCapitalCity(result.get(i).get("capital").asText())
					.setArea(result.get(i).get("area").asText())
					.build();
			states.add(s);
		}
		
		return states;
	}
	
	public static State stateJsonToStateObj(String stateJson) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(stateJson);
		JsonNode response = actualObj.get("RestResponse");
		JsonNode result = response.get("result");
				
		State s = new StateBuilder()
				.setCountry(null)
				.setIdState(result.get("id").asInt())
				.setFullName(result.get("name").asText())
				.setAlpha2Code(result.get("abbr").asText())
				.setLargestCity(result.get("largest_city").asText())
				.setCapitalCity(result.get("capital").asText())
				.setArea(result.get("area").asText())
				.build();

		return s;
	}
	
	
}
