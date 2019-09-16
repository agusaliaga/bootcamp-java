package com.globant.bootcamp.java.weatherapplication.adapters;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.globant.bootcamp.java.weatherapplication.builders.TownBuilder;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.model.Town;
import com.globant.bootcamp.java.weatherapplication.model.Weather;
import com.globant.bootcamp.java.weatherapplication.services.RestYahooClient;
import com.globant.bootcamp.java.weatherapplication.services.RestStatesClient;
import com.globant.bootcamp.java.weatherapplication.services.RestCountriesClient;
import com.globant.bootcamp.java.weatherapplication.transformers.CountryTransformer;
import com.globant.bootcamp.java.weatherapplication.transformers.StateTransformer;
import com.globant.bootcamp.java.weatherapplication.transformers.WeatherTransformer;

public class YahooAPIWeatherAdapter implements YahooAPIWeatherAdapterInterface {
	@Resource
	private RestYahooClient RestYahooClient;
	
	@Resource
	private RestStatesClient RestStatesClient;
	
	@Resource 
	RestCountriesClient RestCountriesClient;
	
	@Override
	public Weather getWeatherToday(String town, String state, String country) throws IOException {
		
		String q = "select * from weather.forecast where woeid in "
				+ "(select woeid from geo.places(1) where text=\" +  " + town + "," + state + "," + country + "\")";
		String format = "json";

		String response = RestYahooClient.getWeather(q, format); 
		
		Weather w = WeatherTransformer.weatherJsonToWeatherObject(response);
		
		//gets state and country data from the other clients
			
		String responseCountry =  RestCountriesClient.getCountryByShortName3(country); //ej: USA, IND	
		Country ctry = CountryTransformer.countryJsonToCountryObj(responseCountry);
		
			
		String responseState = RestStatesClient.getStateByLongCountryCodeShortStateCode(country, state); //ej: USA, AK
		State st = StateTransformer.stateJsonToStateObj(responseState);
		st.setCountry(ctry);
		
		Town twn =  new TownBuilder().setFullName(town).setState(st).build();
		
		w.setTown(twn);
	
		return w;
		
	}
	
	public List<Weather> getWeatherForecast(String town, String state, String country) throws IOException {
		String q = "select * from weather.forecast where woeid in "
				+ "(select woeid from geo.places(1) where text=\" +  " + town + "," + state + "," + country + "\")";
		String format = "json";
		
		String response = RestYahooClient.getWeather(q, format); 
		List<Weather> weathers = WeatherTransformer.JsonToWeatherForecast(response);
		
		//gets state and country data from the other clients
		String responseCountry =  RestCountriesClient.getCountryByShortName3(country); //ej: USA, IND
		Country ctry = CountryTransformer.countryJsonToCountryObj(responseCountry);
				
		String responseState = RestStatesClient.getStateByLongCountryCodeShortStateCode(country, state); //ej: USA, AK
		State st = StateTransformer.stateJsonToStateObj(responseState);
		st.setCountry(ctry);
				
		Town twn =  new TownBuilder().setFullName(town).setState(st).build();
		
		for (Weather weather : weathers) {
			weather.setTown(twn);
		}
		return weathers;
	}	
}
