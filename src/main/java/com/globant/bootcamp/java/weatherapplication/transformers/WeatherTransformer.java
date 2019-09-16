package com.globant.bootcamp.java.weatherapplication.transformers;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.bootcamp.java.weatherapplication.builders.AtmosphereBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.CountryBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.StateBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.TownBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.WeatherBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.WeatherDescriptionBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.WindBuilder;
import com.globant.bootcamp.java.weatherapplication.model.Atmosphere;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.model.Town;
import com.globant.bootcamp.java.weatherapplication.model.Weather;
import com.globant.bootcamp.java.weatherapplication.model.WeatherDescription;
import com.globant.bootcamp.java.weatherapplication.model.Wind;

public class WeatherTransformer {
	
	/*transforms weather JSON for today to a weather Object*/
	public static Weather weatherJsonToWeatherObject(String weatherJson) throws JsonProcessingException, IOException {
			
		Weather w = null;
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(weatherJson);	
		JsonNode query = actualObj.get("query");
		JsonNode results = query.get("results");
		JsonNode channel = results.get("channel");
		JsonNode location = channel.get("location");
		JsonNode item = channel.get("item");
		JsonNode condition = item.get("condition");
		
		
		Country  c = new CountryBuilder().setFullName(location.get("country").asText()).build();
		State s = new StateBuilder().setAlpha2Code(location.get("region").asText()).setCountry(c).build();
		Town t = new TownBuilder().setFullName(location.get("city").asText()).setState(s).build();
		
		JsonNode windJson = channel.get("wind");
		JsonNode atmosphereJson = channel.get("atmosphere");
		
		Wind wind = new WindBuilder().setSpeed(windJson.get("speed").asInt())
					.setDirection(windJson.get("direction").asInt()).build();

		Atmosphere atm = new AtmosphereBuilder().setHumidity(atmosphereJson.get("humidity").asInt())
					.setPressure(atmosphereJson.get("pressure").asDouble())
					.setRising(atmosphereJson.get("rising").asInt())
					.setVisibility(atmosphereJson.get("visibility").asDouble()).build();
		
		String jsonDate = condition.get("date").asText();
		LocalDateTime dt = DateTransformer.YahooDateTimeToDateTime(jsonDate);
		
		WeatherDescription wdesc = new WeatherDescriptionBuilder().setText(condition.get("text").asText()).build();
		
		int tempNowInF = condition.get("temp").asInt();
		int tempNowInC = TemperatureTransformer.fToC(tempNowInF);
		
		JsonNode forecast = item.get("forecast");
		int tempMaxF = forecast.get(0).get("high").asInt();
		int tempMinF = forecast.get(0).get("low").asInt();
		
		int tempMaxC =TemperatureTransformer.fToC(tempMaxF);
		int tempMinC =TemperatureTransformer.fToC(tempMinF);
		
		w = new WeatherBuilder()
				.setTown(t)
				.setWind(wind)
				.setAtmosphere(atm)
				.setDateDay(dt)
				.setTempNow(tempNowInC)
				.setTempMax(tempMaxC)
				.setTempMin(tempMinC)
				.setWeatherDescription(wdesc).build();

			return w;
		}
	

	public static List<Weather> JsonToWeatherForecast (String weatherJson) throws JsonProcessingException, IOException {
		
		List<Weather>weathers = new ArrayList<Weather>();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(weatherJson);	
		JsonNode query = actualObj.get("query");
		JsonNode results = query.get("results");
		JsonNode channel = results.get("channel");
		JsonNode item = channel.get("item");
		JsonNode forecast = item.get("forecast");
		JsonNode location = channel.get("location");
		//JsonNode condition = item.get("condition");
		
		Country  c = new CountryBuilder().setFullName(location.get("country").asText()).build();
		State s = new StateBuilder().setAlpha2Code(location.get("region").asText()).setCountry(c).build();
		Town t = new TownBuilder().setFullName(location.get("city").asText()).setState(s).build();
		
	
		for (int i=1; i<10; i++) {
			String yahoodate = forecast.get(i).get("date").asText();
			LocalDateTime dt = DateTransformer.YahooDateToDateTime(yahoodate);
			DayOfWeek day = dt.getDayOfWeek();
			int tempMaxC = TemperatureTransformer.fToC(forecast.get(i).get("high").asInt());
			int tempMinC = TemperatureTransformer.fToC(forecast.get(i).get("low").asInt());
			WeatherDescription wdesc = new WeatherDescriptionBuilder().setText(forecast.get(i).get("text").asText()).build();
		
			Weather w = new WeatherBuilder()
					.setAtmosphere(null)
					.setWind(null)
					.setDateDay(dt)
					.setTempMax(tempMaxC)
					.setTempMin(tempMinC)
					.setTempNow(0)
					.setTown(t)
					.setWeekDay(day)
					.setWeatherDescription(wdesc)
					.build();
			
			weathers.add(w);
		}

		return weathers;
	}
	
	public static Weather weatherJsonFromModeltoWeatherObject(String weather) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(weather);
		JsonNode town = actualObj.get("town");
		JsonNode state = town.get("state");
		JsonNode country = state.get("country");
		JsonNode atm = actualObj.get("atmosphere");
		JsonNode wind = actualObj.get("wind");
		JsonNode wdesc = actualObj.get("weatherDescription");
		
		Country c = new CountryBuilder().setIdCountry(country.get("idCountry").asInt())
				.setAlpha2Code(country.get("alpha2Code").asText())
				.setAlpha3Code(country.get("alpha3Code").asText())
				.setFullName(country.get("fullName").asText()).build();
		
		State s = new StateBuilder().setIdState(state.get("idState").asInt())
				.setFullName(state.get("fullName").asText())
				.setAlpha2Code(state.get("alpha2Code").asText())
				.setLargestCity(state.get("largestCity").asText())
				.setCapitalCity(state.get("capitalCity").asText())
				.setArea(state.get("area").asText())
				.setCountry(c).build();
		
		Town t =  new TownBuilder().setIdTown(town.get("idTown").asInt())
								.setFullName(town.get("fullName").asText())
								.setState(s).build();
		
		Atmosphere a = new AtmosphereBuilder().setIdAtmosphere(atm.get("idAtmosphere").asInt())
						.setHumidity(atm.get("humidity").asInt())
						.setPressure(atm.get("pressure").asDouble())
						.setRising(atm.get("rising").asInt())
						.setVisibility(atm.get("visibility").asDouble())
						.build();
		
		Wind w = new WindBuilder().setIdWind(wind.get("idWind").asInt())
				.setDirection(wind.get("direction").asInt())
				.setSpeed(wind.get("speed").asInt())
				.build();
		
		WeatherDescription wdscrp = new WeatherDescriptionBuilder()
									.setIdWeatherDescription(wdesc.get("idWeatherDescription").asInt())
									.setText(wdesc.get("text").asText()).build();
		
		
		String dt = actualObj.get("dateDay").asText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dt, formatter);
		
		Weather wthr = new WeatherBuilder()
				.setTown(t)
				.setAtmosphere(a)
				.setWind(w)
				.setWeatherDescription(wdscrp)
				.setDateDay(dateTime)
				.setTempMax(actualObj.get("tempMax").asInt())
				.setTempMin(actualObj.get("tempMin").asInt())
				.setTempNow(actualObj.get("tempNow").asInt())
				.build();
		
		return wthr;
	}
}