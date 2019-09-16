package com.globant.bootcamp.java.weatherapplication.model;

public class WeatherDescription {
	private int idWeatherDescription;
	private String text;
	
	
	public WeatherDescription() {}
	
	public WeatherDescription(String text) {
		this.text=text;
	}
	public WeatherDescription(int idWeatherDescription, String text) {
		this.idWeatherDescription=idWeatherDescription;
		this.text=text;
	}

	public int getIdWeatherDescription() {
		return idWeatherDescription;
	}

	public void setIdWeatherDescription(int idWeatherDescription) {
		this.idWeatherDescription = idWeatherDescription;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "WeatherDescription [id=" + idWeatherDescription + ", text=" + text + "]";
	}
}
