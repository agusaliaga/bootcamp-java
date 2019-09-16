package com.globant.bootcamp.java.weatherapplication.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Component
public class DBManager {

	@Autowired
	StateDAO sd;
	@Autowired
	WeatherDAO wd;
	@Autowired
	TownDAO td;
	@Autowired
	CountryDAO cd;
	@Autowired
	AtmosphereDAO ad;
	@Autowired
	WindDAO wndd;
	@Autowired
	WeatherDescriptionDAO wdd;

	//si no existe lo guardo
	public Boolean insertCountry(Country c) {
		if(cd.selectOneByName(c)==null) {
			if(cd.insert(c)==true) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	//****************************************************************************************/
	//****************************** INSERT STATE ********************************************/
	//****************************************************************************************/
	public Boolean insertState(State s) {
		//caso de que ya haya uno con el mismo nombre
		if(sd.selectOneByName(s)!=null) {
			return false;
		}
		else {
			//caso de que no exista
			//busco si el pais existe
			Country c = new CountryBuilder().setAlpha3Code(s.getCountry().getAlpha3Code())
					.setAlpha2Code(s.getCountry().getAlpha2Code())
					.setFullName(s.getCountry().getFullName())
					.setIdCountry(s.getCountry().getIdCountry())
					.build();
			//existe el pais guardo el estado
			Country ctry = cd.selectOneByLongCode(c);
			if(ctry!=null) {
				s.setCountry(ctry);
				sd.insert(s);
				return true;
			}
			//si no existe el country debo insertar el country
			else {
				//si no estan vacios los campos inserto el country y luego el estado
				if(!c.getFullName().isEmpty()|| !c.getAlpha2Code().isEmpty() || !c.getAlpha3Code().isEmpty())
					cd.insert(c);
				Country country = cd.selectOneByLongCode(c);
				s.setCountry(country);
				sd.insert(s);
				return true;
			}
		}
	}
	//****************************************************************************************/
	//****************************** INSERT WEATHER ******************************************/
	//****************************************************************************************/
	
	public Boolean insertWeather(Weather w) {

		Town t = new TownBuilder().setFullName(w.getTown().getFullName()).setState(w.getTown().getState()).build();
		Town town = td.selectOneByName(t);

		State s = new StateBuilder().setFullName(w.getTown().getState().getFullName())
				.setAlpha2Code(w.getTown().getState().getAlpha2Code())
				.setArea(w.getTown().getState().getArea())
				.setCapitalCity(w.getTown().getState().getCapitalCity())
				.setLargestCity(w.getTown().getState().getLargestCity())
				.setCountry(w.getTown().getState().getCountry())
				.build();
		State state = sd.selectOneByName(s);

		Country c = new CountryBuilder().setFullName(w.getTown().getState().getCountry().getFullName())
				.setAlpha2Code(w.getTown().getState().getCountry().getAlpha2Code())
				.setAlpha3Code(w.getTown().getState().getCountry().getAlpha3Code())
				.build();
		Country country = cd.selectOneByLongCode(c);

		Atmosphere a = new AtmosphereBuilder().setHumidity(w.getAtmosphere().getHumidity())
				.setPressure(w.getAtmosphere().getPressure())
				.setRising(w.getAtmosphere().getRising())
				.setVisibility(w.getAtmosphere().getVisibility()).build();
		Atmosphere atmosphere = ad.selectOneByAttributes(a);

		Wind wi = new WindBuilder().setDirection(w.getWind().getDirection()).setSpeed(w.getWind().getSpeed()).build();
		Wind wind = wndd.selectOneBySpeedAndDirection(wi);

		WeatherDescription wdesc = new WeatherDescriptionBuilder().setText(w.getWeatherDescription().getText()).build();
		WeatherDescription weatherDescription = wdd.selectOneByName(wdesc);


		if(wd.selectOnebyDateAndTown(w)!=null) {
			return false;
		}
		else {
			if(country==null) {
				cd.insert(c);
				Country ctry =  cd.selectOneByName(c);
				s.setCountry(ctry);
				//state.setCountry(ctry);
			}
			else {
				s.setCountry(country);
				state.setCountry(country);
				
			}
			if (state==null) { 
				sd.insert(s);
				State st = sd.selectOneByName(s);
				t.setState(st);

			}
			else {
				t.setState(state);
				state.setCountry(country);

			}			
			if(town==null) {
				td.insert(t);
				Town twn = td.selectOneByName(t);
				w.setTown(twn);
			}
			else {
				t.setState(state);
				town.setState(state);
				w.setTown(town);
			}	
			if(atmosphere==null) {
				ad.insert(a);
				Atmosphere atm = ad.selectOneByAttributes(a);
				w.setAtmosphere(atm);
			}
			else {
				w.setAtmosphere(atmosphere);
			}
			if(wind==null) {
				wndd.insert(wi);

			}
			if(weatherDescription==null) {
				wdd.insert(wdesc);

			}
			Town finalTown = td.selectOneByName(t);
			State finalState = sd.selectOneByName(t.getState());
			finalTown.setState(finalState);

			Country finalCountry = cd.selectOne(t.getState().getCountry());
			finalState.setCountry(finalCountry);

			Atmosphere finalAtmosphere = ad.selectOneByAttributes(a);
			w.setAtmosphere(finalAtmosphere);

			Wind finalWind = wndd.selectOneBySpeedAndDirection(wi);
			w.setWind(wi);

			WeatherDescription finalWdesc =  wdd.selectOneByName(wdesc);
			w.setWeatherDescription(finalWdesc);

			Weather wthr = new WeatherBuilder().setTown(finalTown)
					.setAtmosphere(finalAtmosphere)
					.setWind(finalWind)
					.setDateDay(w.getDateDay())
					.setTempMax(w.getTempMax())
					.setTempMin(w.getTempMin())
					.setTempNow(w.getTempNow())
					.setWeatherDescription(finalWdesc)
					.build();

			if(wd.insert(wthr)==true) {
				return true;
			}
		}
		return false;
	} 	
	//****************************************************************************************/
	//****************************** INSERT FORECAST *****************************************/
	//****************************************************************************************/
	public Boolean insertForecast(List<Weather> w) {
		
		Town t = new TownBuilder().setFullName(w.get(0).getTown().getFullName()).setState(w.get(0).getTown().getState()).build();
		Town town = td.selectOneByName(t);
		
		State s = new StateBuilder().setFullName(w.get(0).getTown().getState().getFullName())
				.setAlpha2Code(w.get(0).getTown().getState().getAlpha2Code())
				.setArea(w.get(0).getTown().getState().getArea())
				.setCapitalCity(w.get(0).getTown().getState().getCapitalCity())
				.setLargestCity(w.get(0).getTown().getState().getLargestCity())
				.setCountry(w.get(0).getTown().getState().getCountry())
				.build();
		State state = sd.selectOneByName(s);
		
		Country c = new CountryBuilder().setFullName(w.get(0).getTown().getState().getCountry().getFullName())
				.setAlpha2Code(w.get(0).getTown().getState().getCountry().getAlpha2Code())
				.setAlpha3Code(w.get(0).getTown().getState().getCountry().getAlpha3Code())
				.build();
		Country country = cd.selectOneByName(c);
		
		if(country==null) {
			cd.insert(c);
			Country ctry =  cd.selectOneByName(c);
			s.setCountry(ctry);
			//state.setCountry(ctry);
		}
		else {
			//s.setCountry(cd.selectOneByName(w.getTown().getState().getCountry()));
			s.setCountry(country);
			//state.setCountry(cd.selectOne(state.getCountry()));
			
		}
		if (state==null) { //no existe ese state lo guardo
			sd.insert(s);
			State st = sd.selectOneByName(s);
			t.setState(st);
			
		}
		else {
			t.setState(state);
			state.setCountry(country);
			
		}	
		if(town==null) {
			td.insert(t);
			Town twn = td.selectOneByName(t);
			for (Weather weather : w) {
				weather.setTown(twn);
			}
		}
		else {
			t.setState(state);
			town.setState(state);
			for (Weather weather : w) {
				weather.setTown(town);
			}
			
		}		
		/*Iterate the array and insert the weathers in the array*/
		
		for (Weather weather : w ) {
			try {
				if(wd.selectOnebyDateAndTown(weather)!=null) {
					continue;
				}
				else {
					
					WeatherDescription wdesc = new WeatherDescriptionBuilder().setText(weather.getWeatherDescription().getText()).build();
					WeatherDescription weatherDescription = wdd.selectOneByName(wdesc);
					
					if(weatherDescription==null) {
						wdd.insert(wdesc);

					}
					Town finalTown = td.selectOneByName(t);
					State finalState = sd.selectOneByName(t.getState());
					finalTown.setState(finalState);

					Country finalCountry = cd.selectOne(t.getState().getCountry());
					finalState.setCountry(finalCountry);

					WeatherDescription finalWdesc =  wdd.selectOneByName(wdesc);
					weather.setWeatherDescription(finalWdesc);

					Weather wthr = new WeatherBuilder().setTown(finalTown)
							.setAtmosphere(null)
							.setWind(null)
							.setDateDay(weather.getDateDay())
							.setTempMax(weather.getTempMax())
							.setTempMin(weather.getTempMin())
							.setTempNow(weather.getTempNow())
							.setWeatherDescription(finalWdesc)
							.build();

					wd.insertForecast(wthr);
				}	
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}