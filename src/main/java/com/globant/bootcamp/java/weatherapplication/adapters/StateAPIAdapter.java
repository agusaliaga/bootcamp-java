package com.globant.bootcamp.java.weatherapplication.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.proxies.CountryProxy;
import com.globant.bootcamp.java.weatherapplication.services.RestStatesClient;
import com.globant.bootcamp.java.weatherapplication.services.RestCountriesClient;
import com.globant.bootcamp.java.weatherapplication.transformers.CountryTransformer;
import com.globant.bootcamp.java.weatherapplication.transformers.StateTransformer;

public class StateAPIAdapter implements StateAPIAdapterInterface {
	@Resource
	private RestStatesClient RestStatesClient;
	
	@Resource
	private RestCountriesClient RestCountriesClient;
	
	@Autowired
	public static CountryProxy cp;
	
	@Override
	public List<State> getStateByLongCountryCode(String alpha3) {
				
		String response = RestStatesClient.getStateByLongCountryCode(alpha3);
		
		String responseCountry =  RestCountriesClient.getCountryByShortName3(alpha3);
		Country ctry = new Country();
		try {
			ctry = CountryTransformer.countryJsonToCountryObj(responseCountry);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<State> s = new ArrayList<State>();
		try {
			s = StateTransformer.stateJSONtoStateArray(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (State state : s) {
			state.setCountry(ctry);
		}
		
		return s;
	}
	//catch null pointer en el caso de que lo que busque no exista
	@Override
	public State getStateByLongCountryCodeShortStateCode(String alpha3country, String alpha2state) {
		
		String response = RestStatesClient.getStateByLongCountryCodeShortStateCode(alpha3country, alpha2state);
		State s = new State();
		try {
			s = StateTransformer.stateJsonToStateObj(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String responseCountry =  RestCountriesClient.getCountryByShortName3(alpha3country);
		Country ctry = new Country();
		try {
			ctry = CountryTransformer.countryJsonToCountryObj(responseCountry);
		} catch (IOException e) {
			e.printStackTrace();
		}
		s.setCountry(ctry);
		
		return s;
	}
	
}
