package com.globant.bootcamp.java.weatherapplication.builders;

import com.globant.bootcamp.java.weatherapplication.model.WeatherDescription;

public class WeatherDescriptionBuilder {
	public int idWeatherDescription=1;
	public String text="Rain";
	
	public WeatherDescriptionBuilder setIdWeatherDescription(int idWeatherDescription) {
		this.idWeatherDescription = idWeatherDescription;
		return this;
	}
	public WeatherDescriptionBuilder setText(String text) {
		this.text = text;
		return this;
	}
	public WeatherDescription build() {
		return new WeatherDescription(idWeatherDescription,text);
	}
	
	
}
