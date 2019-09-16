package com.globant.bootcamp.java.weatherapplication.proxies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.bootcamp.java.weatherapplication.adapters.StateAPIAdapter;
import com.globant.bootcamp.java.weatherapplication.builders.CountryBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.StateBuilder;
import com.globant.bootcamp.java.weatherapplication.db.CountryDAO;
import com.globant.bootcamp.java.weatherapplication.db.DBManager;
import com.globant.bootcamp.java.weatherapplication.db.StateDAO;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.services.RestStatesClient;

public class StateProxy implements RestStatesClient{

	@Autowired
	private StateAPIAdapter sa;
	@Autowired
	private StateDAO sd;
	@Autowired
	private CountryDAO cd;
	@Autowired
	private DBManager db;
	
	private ObjectMapper mapper = new ObjectMapper();

	//*********************************************************************************************/
	//*************************** GET ALL STATES FROM ONE COUNTRY *********************************/
	//*********************************************************************************************/

	@Override
	//shows all states of one country
	public String getStateByLongCountryCode(String alpha3) {
		if(alpha3.length()!=3 || alpha3.matches(".*\\d+.*")) {
			return "The country code must be of length 3 and cannot contain numbers";
		}
		else {		
			try {
				List<State> clientResponse = sa.getStateByLongCountryCode(alpha3);
				
				if(clientResponse.size()==0) {
					Country ctry = new CountryBuilder().setAlpha3Code(alpha3).build();
					Country c = cd.selectOneByLongCode(ctry);	
					if(c==null) {
						return "There is no matching Country";
					}
					List<State>daoResponse = sd.selectOneByCountry(c);
					for (State state : daoResponse) {
						state.setCountry(c);
					}
					try {
						return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(daoResponse);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				}
				else {
					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientResponse);
				}
			}
			catch (Exception ex) {
				Country ctry = new CountryBuilder().setAlpha3Code(alpha3).build();
				Country c = cd.selectOneByLongCode(ctry);

				if(c==null) {
					return "There is no matching Country";
				}
				List<State>daoResponse = sd.selectOneByCountry(c);

				try {
					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(daoResponse);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		return "There is no matching Country";

	}
	//*********************************************************************************************/
	//*** GET ONE STATE OF ONE COUNTRY USING THE STATE'S SHORT CODE AND COUNTRY'S LONG CODE *******/
	//*********************************************************************************************/
	@Override
	public String getStateByLongCountryCodeShortStateCode(String countryAlpha3, String stateAlpha2){
		//check input
		if(countryAlpha3.length()!=3 || countryAlpha3.matches(".*\\d+.*") 
				|| stateAlpha2.length()!=2 || stateAlpha2.matches(".*\\d+.*")) {
			return "The country code must be of length 3 and cannot contain numbers";
		}
		else {
			try {
				State clientResponse = sa.getStateByLongCountryCodeShortStateCode(countryAlpha3, stateAlpha2);
				db.insertState(clientResponse);
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientResponse);
			}
			catch (Exception ex) {
				Country ctry = new CountryBuilder().setAlpha3Code(countryAlpha3).build();
				Country country = cd.selectOneByLongCode(ctry);
				Country c = new CountryBuilder().setIdCountry(country.getIdCountry())
						.setFullName(country.getFullName()).setAlpha2Code(country.getAlpha2Code())
						.setAlpha3Code(country.getAlpha3Code())
						.build();

				State st = new StateBuilder().setAlpha2Code(stateAlpha2).build();
				State s = sd.selectOneByCountryAndShortCode(st, country);

				if(s == null) {
					return "There is no matching record";
				}
				try {
					s.setCountry(c);
					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(s);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		return "There is no matching record";

	}
	//*********************************************************************************************/
	//******************************************* INSERT STATE ************************************/
	//*********************************************************************************************/

	public String insertState(State state) {			
		if(state.getFullName().isEmpty() 
				|| state.getAlpha2Code().isEmpty() 
				|| state.getArea().isEmpty()
				|| state.getLargestCity().isEmpty() 
				|| state.getCapitalCity().isEmpty()
				|| state.getCountry().getAlpha3Code().isEmpty()) {
			return "Check that the fields aren't empty in the JSON being posted";
		}
		else {

			if(db.insertState(state)==true) {
				return "The state has been inserted";
			}
			else {
				return "The state couldnt be inserted";
			}
		}
	}		
}
