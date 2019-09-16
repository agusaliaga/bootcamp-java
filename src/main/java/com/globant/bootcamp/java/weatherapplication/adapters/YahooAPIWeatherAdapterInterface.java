package com.globant.bootcamp.java.weatherapplication.adapters;

import java.io.IOException;
import java.util.List;

import com.globant.bootcamp.java.weatherapplication.model.Weather;

public interface YahooAPIWeatherAdapterInterface {
	
	public Weather getWeatherToday(String town, String state, String country) throws IOException;
	
	public List<Weather> getWeatherForecast(String town, String state, String country) throws IOException;
}
