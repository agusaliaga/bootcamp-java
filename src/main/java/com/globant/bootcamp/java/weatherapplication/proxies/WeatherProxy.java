package com.globant.bootcamp.java.weatherapplication.proxies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.bootcamp.java.weatherapplication.adapters.YahooAPIWeatherAdapter;
import com.globant.bootcamp.java.weatherapplication.builders.CountryBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.StateBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.TownBuilder;
import com.globant.bootcamp.java.weatherapplication.builders.WeatherBuilder;
import com.globant.bootcamp.java.weatherapplication.db.AtmosphereDAO;
import com.globant.bootcamp.java.weatherapplication.db.CountryDAO;
import com.globant.bootcamp.java.weatherapplication.db.DBManager;
import com.globant.bootcamp.java.weatherapplication.db.StateDAO;
import com.globant.bootcamp.java.weatherapplication.db.TownDAO;
import com.globant.bootcamp.java.weatherapplication.db.WeatherDAO;
import com.globant.bootcamp.java.weatherapplication.db.WeatherDescriptionDAO;
import com.globant.bootcamp.java.weatherapplication.db.WindDAO;
import com.globant.bootcamp.java.weatherapplication.model.Atmosphere;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.model.Town;
import com.globant.bootcamp.java.weatherapplication.model.Weather;
import com.globant.bootcamp.java.weatherapplication.model.WeatherDescription;
import com.globant.bootcamp.java.weatherapplication.model.Wind;
import com.globant.bootcamp.java.weatherapplication.services.RestYahooClient;

public class WeatherProxy implements RestYahooClient{

	@Autowired 
	YahooAPIWeatherAdapter wa;
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

	@Autowired
	DBManager db;

	private ObjectMapper mapper = new ObjectMapper();


	//*********************************************************************************************/
	//************************************* 1)GET WEATHER TODAY ***********************************/
	//*********************************************************************************************/
	//**************************************** inserts a new weather ******************************/

	public String getWeatherToday (String town, String state, String country) throws IOException {
		//check input
		if(town.isEmpty() || town.matches(".*\\d+.*") ||
				state.length()!=2 || state.matches(".*\\d+.*") ||
				country.length()!=3 || country.matches(".*\\d+.*")) {
			return "Something is wrong with the input "
					+ "\nThe town name cannot contain numbers"
					+ "\nThe state code must be of length 2 and cannot contain numbers"
					+ "\nThe country code must be of length 2 and cannot contain numbers"
					+ "\nCheck your input and try again!";
		}
		else {
			try {
				Weather clientResponse = wa.getWeatherToday(town, state, country);
				db.insertWeather(clientResponse);
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientResponse);
			}
			//si no esta en el cliente o no esta conectado busca info en BD
			catch (Exception ex) {

				try {
					Country c = new CountryBuilder().setAlpha3Code(country).build();
					Country ctry = cd.selectOneByLongCode(c);

					State s = new StateBuilder().setAlpha2Code(state).build();
					State st = sd.selectOneByShortCode(s);
					st.setCountry(ctry);

					Town t = new TownBuilder().setFullName(town).build();
					Town twn = td.selectOneByName(t);
					twn.setState(st);

					Weather w =  wd.getToday(twn);

					Wind wind = wndd.selectOne(w.getWind());
					Atmosphere atm = ad.selectOne(w.getAtmosphere());
					WeatherDescription wdesc = wdd.selectOne(w.getWeatherDescription());

					w.setAtmosphere(atm);
					w.setTown(twn);
					w.setWind(wind);
					w.setWeatherDescription(wdesc);

					try {
						return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(w);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				}
				catch(Exception e) {
					return "There is no matching record";
				}
			}
		}
		return "Error";
	}

	//*********************************************************************************************/
	//************************************* 2)GET FORECAST ****************************************/
	//*********************************************************************************************/
	//************************************** inserts a forecast ***********************************/
	public String getWeatherForecast(String town, String state, String country) {
		if(town.isEmpty() || town.matches(".*\\d+.*") ||
				state.length()!=2 || state.matches(".*\\d+.*") ||
				country.length()!=3 || country.matches(".*\\d+.*")) {
			return "Something is wrong with the input "
					+ "\nThe town name cannot contain numbers"
					+ "\nThe state code must be of length 2 and cannot contain numbers"
					+ "\nThe country code must be of length 2 and cannot contain numbers"
					+ "\nCheck your input and try again!";
		}
		else {
			try {
				List<Weather> clientResponse = wa.getWeatherForecast(town, state, country);
				db.insertForecast(clientResponse);
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientResponse);
			}
			//si no esta en el cliente o no esta conectado busca info en BD
			catch (Exception e) {
				try {
					Country c = new CountryBuilder().setAlpha3Code(country).build();
					Country ctry = cd.selectOneByLongCode(c);

					State s = new StateBuilder().setAlpha2Code(state).build();
					State st = sd.selectOneByShortCode(s);
					st.setCountry(ctry);

					Town t = new TownBuilder().setFullName(town).build();
					Town twn = td.selectOneByName(t);
					twn.setState(st);

					List<Weather> forecast = wd.getForecast(twn);
					List<Weather> wf = new ArrayList<>();

					if(forecast != null) {

						for (Weather weather : forecast) {
							WeatherDescription wdesc = wdd.selectOne(weather.getWeatherDescription());
							Weather wthr = new WeatherBuilder().setIdWeather(weather.getIdWeather()).setAtmosphere(null).setWind(null).setTempNow(0).setDateDay(weather.getDateDay())
									.setTempMax(weather.getTempMax()).setTown(twn).setWeekDay(weather.getWeekDay()).setWeatherDescription(wdesc).build();
							wf.add(wthr);
						}
					}
					else {
						return "There are no matching records";
					}
					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wf);
				}
				catch(Exception ex) {
					return "There are no matching records";
				}
			}
		}
	}
	//**********************************************************************************************/
	//************************************* 3)INSERT TODAY manually ********************************/
	//**********************************************************************************************/
	public String insertWeather(Weather weather) {

		if(db.insertWeather(weather)==true) {
			return "The weather has been inserted";
		}
		else  {
			return "The weather couldnt be inserted";
		}
	}
	//**********************************************************************************************/
	//******************************** INSERT FORECAST MANUALLY ************************************/
	//**********************************************************************************************/
	public String insertForecast(List<Weather> w) {

		if(w.size()!=0) {
			for (Weather weather : w) {
				try {
					wd.insertForecast(weather);
				}
				catch (Exception e) {
					return e.toString();
				}
			}
			return "The registers were inserted";
		}
		else {
			return "The input couldn't be read";
		}
	}
	//**********************************************************************************************/
	//******************************** 4)GET LAST INSERTED REGISTER ********************************/
	//**********************************************************************************************/
	public String getLastInsertedRegister() {

		try {
			Weather w = wd.selectLastRegister();

			Town town = td.selectOne(w.getTown());
			Wind wind = wndd.selectOne(w.getWind());
			Atmosphere atm = ad.selectOne(w.getAtmosphere());
			WeatherDescription wdesc = wdd.selectOne(w.getWeatherDescription());

			int stateId =  town.getState().getIdState();

			State s = new StateBuilder().setIdState(stateId).build();
			State state = sd.selectOne(s);
			town.setState(state);

			Country c = new CountryBuilder().setIdCountry(state.getCountry().getIdCountry()).build();
			Country ctry = cd.selectOne(c);
			state.setCountry(ctry);

			w.setTown(town);
			w.setAtmosphere(atm);
			w.setWind(wind);
			w.setWeatherDescription(wdesc);

			try {
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(w);
			} catch (JsonProcessingException e) {
				return "Error " + e.toString();
			}
		}
		catch (Exception e){
			return "Error";

		}
	}

	//**********************************************************************************************/
	//***************** GET TODAY'S WEATHER FOR ALL THE CITIES OF ONE STATE ************************/
	//**********************************************************************************************/
	public String getAllWeathersByStateAndCountry(Country ctry, State st) {

		try {
			List<Weather> wf = new ArrayList<>();
			List<Weather> listaweathers = wd.selectAllByState(ctry,st);

			if(listaweathers != null) {

				for (Weather weather : listaweathers) {
					Town town = td.selectOne(weather.getTown());
					Wind wind = wndd.selectOne(weather.getWind());
					Atmosphere atm = ad.selectOne(weather.getAtmosphere());
					WeatherDescription wdesc = wdd.selectOne(weather.getWeatherDescription());
					Weather clima = new WeatherBuilder().setIdWeather(weather.getIdWeather()).setAtmosphere(atm).setWind(wind).setTempNow(weather.getTempNow()).setDateDay(weather.getDateDay())
							.setTempMax(weather.getTempMax()).setTown(town).setWeekDay(weather.getWeekDay()).setWeatherDescription(wdesc).build();
					wf.add(clima);

				}
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wf);
			}
		}
		catch(Exception e) {
			return "Error";
		}
		return "Error";
	}

	//**********************************************************************************************/
	//******************************* UPDATE WEATHER BY ID *****************************************/
	//**********************************************************************************************/
	
	public String updateWeatherById(Weather wthr) {
		try {
			wd.update(wthr);
			return "The weather was updated";
		}
		catch(Exception e) {
			return "Error";
		}
	}



	@Override
	public String getWeather(String q, String format) {
		// TODO Auto-generated method stub
		return null;
	}
}