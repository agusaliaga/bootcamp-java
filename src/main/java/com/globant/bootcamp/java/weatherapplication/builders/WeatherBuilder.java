package com.globant.bootcamp.java.weatherapplication.builders;


import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.globant.bootcamp.java.weatherapplication.model.Atmosphere;
import com.globant.bootcamp.java.weatherapplication.model.Town;
import com.globant.bootcamp.java.weatherapplication.model.Weather;
import com.globant.bootcamp.java.weatherapplication.model.WeatherDescription;
import com.globant.bootcamp.java.weatherapplication.model.Wind;

public class WeatherBuilder {
	private int idWeather = 1;
	private Town town =  new TownBuilder().build();
	private Atmosphere atmosphere = new AtmosphereBuilder().build();
	private Wind wind = new WindBuilder().build();
	private LocalDateTime dateDay = LocalDateTime.now();
	private DayOfWeek weekDay = LocalDateTime.now().getDayOfWeek();
	private WeatherDescription weatherDescription = new WeatherDescriptionBuilder().build();
	private int tempNow = 25;
	private int tempMin = 18;
	private int tempMax = 32;
	
	
	
	public WeatherBuilder setIdWeather(int idWeather) {
		this.idWeather = idWeather;
		return this;
	}
	public WeatherBuilder setTown(Town town) {
		this.town = town;
		return this;
	}
	public WeatherBuilder setAtmosphere(Atmosphere atmosphere) {
		this.atmosphere = atmosphere;
		return this;
	}
	public WeatherBuilder setWind(Wind wind) {
		this.wind = wind;
		return this;
	}
	public WeatherBuilder setDateDay(LocalDateTime dateDay) {
		this.dateDay = dateDay;
		return this;
	}
	public WeatherBuilder setWeekDay(DayOfWeek weekDay) {
		this.weekDay = weekDay;
		return this;
	}
	public WeatherBuilder setWeatherDescription(WeatherDescription weatherDescription) {
		this.weatherDescription = weatherDescription;
		return this;
	}
	public WeatherBuilder setTempNow(int tempNow) {
		this.tempNow = tempNow;
		return this;
	}
	public WeatherBuilder setTempMin(int tempMin) {
		this.tempMin = tempMin;
		return this;
	}
	public WeatherBuilder setTempMax(int tempMax) {
		this.tempMax = tempMax;
		return this;
	}
	
	public Weather build() {
		return new Weather(idWeather,town,atmosphere,wind, tempNow,tempMax,tempMin,dateDay,weekDay, weatherDescription);
	}
	
	
	
}
