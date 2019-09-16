package com.globant.bootcamp.java.weatherapplication.controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globant.bootcamp.java.weatherapplication.builders.CountryBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.StateBuilder;
import com.globant.bootcamp.java.weatherapplication.db.CountryDAO;
import com.globant.bootcamp.java.weatherapplication.db.StateDAO;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.model.Weather;
import com.globant.bootcamp.java.weatherapplication.proxies.WeatherProxy;
import com.globant.bootcamp.java.weatherapplication.transformers.WeatherTransformer;

@RestController
@RequestMapping("/weather/")
public class WeatherController {
	@Autowired
	CountryDAO cd;
	@Autowired
	StateDAO sd;
	@Autowired
	private WeatherProxy wp;

	ObjectMapper mapper = new ObjectMapper();

	//*********************************************************************************************/
	//************************************* 1)GET WEATHER TODAY ***********************************/
	//*********************************************************************************************/
	
	@RequestMapping(value = "/get/today/town/", method = RequestMethod.GET, produces ="application/json")
	public ResponseEntity<String> getWeatherTodayByTownStateCountry(
			@RequestParam(value="town", required=true) String town,
			@RequestParam(value="state", required=true) String state,
			@RequestParam(value="country", required=true) String country) 
					throws IOException{   	

		String result = wp.getWeatherToday(town, state, country);

		if(result.equals("There is no matching record")) {
			return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
		}
		else {
			if(result.equals("Error")) {
				return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
			}
		}	
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	//********************************************************************************************/
	//*********************************** 2) GET FORECAST ***************************************/
	//********************************************************************************************/

	@RequestMapping(value = "/get/forecast/town/", method = RequestMethod.GET, produces ="application/json")
	public ResponseEntity<String> getWeatherForecastByTownStateCountry(
			@RequestParam(value="town", required=true) String town,
			@RequestParam(value="state", required=true) String state,
			@RequestParam(value="country", required=true) String country) 
					throws IOException{   	

		String result = wp.getWeatherForecast(town, state, country);

		if(result.equals("There are no matching records")) {
			return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);

	}

	//*********************************************************************************************/
	//*********************************3) INSERT WEATHER TODAY MANUALLY ***************************/
	//*********************************************************************************************/
		
	@RequestMapping(path = "/new/today/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> insertWeatherTodayJson(@RequestBody String weather) throws IOException {
				
		Weather wthr = WeatherTransformer.weatherJsonFromModeltoWeatherObject(weather);
		String result = wp.insertWeather(wthr);

		if(result.equals("The weather has been inserted")) {
			return new ResponseEntity<String>("The weather has been inserted ", HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<String>("The weather couldnt be inserted ", HttpStatus.BAD_REQUEST);
		}		
	}
		
	//*********************************************************************************************/
	//*********************************4) INSERT FORECAST MANUALLY ********************************/
	//*********************************************************************************************/
	
	@RequestMapping(path = "/new/forecast/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> insertWeatherForecast(@RequestBody String weather) throws IOException {
		
		List<Weather> w = mapper.readValue(weather, mapper.getTypeFactory().constructCollectionType(List.class, Weather.class));

		String result = wp.insertForecast(w);
		if(result.equals("The registers were inserted")) {
			return new ResponseEntity<String>("The register was created ", HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("The register was not created ", HttpStatus.BAD_REQUEST);
	}

	//*********************************************************************************************/
	//********************* 4) GET LAST INSERTED REGISTER FROM DB *********************************/
	//*********************************************************************************************/
	
	@RequestMapping(path = "/get/last/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> getLastInsertedRegister() throws IOException {
	
		String result = wp.getLastInsertedRegister();
		if(result.equals("Error")) {
			return new ResponseEntity<String>("Couldn't Connect to DB or there are no registers", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(result, HttpStatus.CREATED);
	}

	//*********************************************************************************************/
	//********** 3) GET ALL WEATHERS (of all towns) FROM ONE STATE of one country  ****************/
	//*********************************************************************************************/

	@RequestMapping(value="/get/today/state/", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<String> getAllWeathersByState(
			@RequestParam(value="country", required=true) String c, 
			@RequestParam(value="state", required=true) String s) throws JsonProcessingException  {

		Country country = new CountryBuilder().setAlpha3Code(c).build();
		State state = new StateBuilder().setAlpha2Code(s).build();
		
		Country ctry = cd.selectOneByLongCode(country);
		State st = sd.selectOneByShortCode(state);
		
		String result = wp.getAllWeathersByStateAndCountry(ctry,st);
				
		if(result.equals("Error")) {
			return new ResponseEntity<String>("Not Found ", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}	

	//********************************************************************************************/
	//**************************** 5) UPDATE WEATHER BY ID ***************************************/
	//********************************************************************************************/

	//JSON
	@RequestMapping(path = "/update/", method = RequestMethod.PUT, consumes = "application/json", produces ="text/html")
	public ResponseEntity<String> updateWeatherByIdJson(@RequestBody String weather) throws IOException {
		Weather w =  new Weather();
		
		try {
			w = mapper.readValue(weather, Weather.class);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		String result = wp.updateWeatherById(w);
		
		if(result=="Error") {
			return new ResponseEntity<String>("The register couldn't be updated ", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("The register was updated ", HttpStatus.ACCEPTED);
	}


//	//*********************************************************************************************/
//	//*************************** EXTRA: GET WEATHER BY WEATHER ID ********************************/
//	//*********************************************************************************************/
//
//	@RequestMapping(value="/get/id/", produces = "application/json", method = RequestMethod.GET)
//	public ResponseEntity<String> getWeatherById(@RequestParam(value="id", required=true) int id) throws JsonProcessingException{   	
//
//		Weather w = new WeatherBuilder().setIdWeather(id).build();
//		
//		if(wd.selectOne(w)!=null) {
//			Weather weather = wd.selectOne(w);
//			Town town = td.selectOne(weather.getTown());
//			Wind wind = windd.selectOne(weather.getWind());
//			Atmosphere atm = ad.selectOne(weather.getAtmosphere());
//			WeatherDescription wdesc = wdd.selectOne(weather.getWeatherDescription());
//
//			Weather wfinal = new WeatherBuilder().setIdWeather(weather.getIdWeather()).setAtmosphere(atm).setWind(wind).
//					setTempNow(weather.getTempNow()).setDateDay(weather.getDateDay())
//					.setTempMax(weather.getTempMax()).setTempMin(weather.getTempMin())
//					.setTown(town).setWeekDay(weather.getWeekDay()).setWeatherDescription(wdesc).build();
//
//			String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wfinal);
//			return new ResponseEntity<String>(result, HttpStatus.OK);
//
//		}
//		else {
//			return new ResponseEntity<String>("Not found ", HttpStatus.NOT_FOUND);
//		}
//	}	

}