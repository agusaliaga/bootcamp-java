package com.globant.bootcamp.java.weatherapplication.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@JsonInclude(Include.NON_NULL)
public class Weather {
	private int idWeather;
	private Town town;
	private Atmosphere atmosphere;
	private Wind wind;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
	private LocalDateTime dateDay;
	private DayOfWeek weekDay;
	private WeatherDescription weatherDescription;
	private int tempNow;
	private int tempMin;
	private int tempMax;

	public Weather () {}
	
	/* Constructor 1:
	 * Takes all the attributes for a Weather Object*/
	public Weather(int idWeather, Town town, Atmosphere atmosphere, Wind wind, 
			int tempNow, int tempMax, int tempMin, LocalDateTime dateDay,
			DayOfWeek weekDay, WeatherDescription weatherDescription) {
		this.idWeather=idWeather;
		this.town = town;
		this.atmosphere = atmosphere;
		this.wind = wind;
		this.tempNow = tempNow;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
		this.dateDay = dateDay;
		this.weekDay = weekDay;
		this.weatherDescription = weatherDescription;
	}
	
	public Weather(Town town, Atmosphere atmosphere, Wind wind, 
			int tempNow, int tempMax, int tempMin, LocalDateTime dateDay,
			DayOfWeek weekDay, WeatherDescription weatherDescription) {
		this.town = town;
		this.atmosphere = atmosphere;
		this.wind = wind;
		this.tempNow = tempNow;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
		this.dateDay = dateDay;
		this.weekDay = weekDay;
		this.weatherDescription = weatherDescription;
	}
	
	
	/* Constructor 2:
	 * in case i want to enter only the parameters for extended forecast
	 * i wouldn't know the current temperature, for a future day, and it 
	 * would be set to 0 until it is updated*/
	public Weather(Town town, LocalDateTime dateDay, DayOfWeek weekDay, WeatherDescription weatherDescription, int tempMin,
			int tempMax) {
		this.town = town;
		this.dateDay = dateDay;
		this.weekDay = weekDay;
		this.weatherDescription = weatherDescription;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
	}
	
	/* Constructor 3:
	 * in case i want to enter only the parameters for TODAY's WEATHER conditions
	 * in the requirements the user wants to enter only current temperature.
	 * date and weather description, the other attributes will be set to default 
	 * until updated, the state always have to be present there cannot be a weather
	 * object without corresponding to a state*/
	
	public Weather(Town town, LocalDateTime dateDay, int tempNow, WeatherDescription weatherDescription) {
		this.town = town;
		this.dateDay = dateDay;
		this.weatherDescription = weatherDescription;
		this.tempNow = tempNow;
	}
	
	public int getIdWeather() {
		return idWeather;
	}
	public void setIdWeather(int idWeather) {
		this.idWeather = idWeather;
	}

	public Town getTown() {
		return town;
	}
	public void setTown(Town town) {
		this.town = town;
	}
	public Atmosphere getAtmosphere() {
		return atmosphere;
	}

	public void setAtmosphere(Atmosphere atmosphere) {
		this.atmosphere = atmosphere;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public int getTempNow() {
		return tempNow;
	}

	public void setTempNow(int tempNow) {
		this.tempNow = tempNow;
	}

	public int getTempMax() {
		return tempMax;
	}

	public void setTempMax(int tempMax) {
		this.tempMax = tempMax;
	}

	public int getTempMin() {
		return tempMin;
	}

	public void setTempMin(int tempMin) {
		this.tempMin = tempMin;
	}

	public LocalDateTime getDateDay() {
		return dateDay;
	}


	public void setDateDay(LocalDateTime dateDay) {
		this.dateDay = dateDay;
	}


	public DayOfWeek getWeekDay() {
		return weekDay;
	}


	public void setWeekDay(DayOfWeek weekDay) {
		this.weekDay = weekDay;
	}


	public WeatherDescription getWeatherDescription() {
		return weatherDescription;
	}


	public void setWeatherDescription(WeatherDescription weatherDescription) {
		this.weatherDescription = weatherDescription;
	}
	
	
	@Override
	public String toString() {
		return "Weather [town=" + town + ", atmosphere=" + atmosphere + ", wind=" + wind + ", dateDay=" + dateDay
				+ ", weekDay=" + weekDay + ", weatherDescription=" + weatherDescription + ", tempNow=" + tempNow
				+ ", tempMin=" + tempMin + ", tempMax=" + tempMax + "]";
	}
	
	
	
	
}
