package com.globant.bootcamp.java.weatherapplication.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.globant.bootcamp.java.weatherapplication.connection.DBConnection;
import com.globant.bootcamp.java.weatherapplication.model.Atmosphere;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.model.Town;
import com.globant.bootcamp.java.weatherapplication.model.Weather;
import com.globant.bootcamp.java.weatherapplication.model.WeatherDescription;
import com.globant.bootcamp.java.weatherapplication.model.Wind;

public class WeatherDAO extends DAOPrototype implements DAO<Weather> {
	

	//*****************************************************************************/
	//***************************** INSERT TODAY **********************************/
	//*****************************************************************************/
	public Boolean insert(Weather w) {
		
		try {
			LocalDateTime date = w.getDateDay();
			Timestamp t = Timestamp.valueOf(date);	
			st = DBConnection.getConnection().prepareStatement(queryGen("weathers","insert"));
			st.setInt(1, w.getTown().getIdTown());
			st.setInt(2, w.getAtmosphere().getIdAtmosphere());
			st.setInt(3, w.getWind().getIdWind());
			st.setInt(4, w.getTempNow());
			st.setInt(5, w.getTempMax());
			st.setInt(6, w.getTempMin());
			st.setInt(6, w.getTempMin());
			st.setTimestamp(7, t);
			st.setInt(8, w.getWeatherDescription().getIdWeatherDescription());
			
			if(st.executeUpdate()==1) {
				return true;
			}
			rs = st.getGeneratedKeys();
			
			while(rs.next()) {
				w.setIdWeather(rs.getInt(1));
			}
		}	
		catch(SQLException e) {
			return false;
		}
		finally {
			closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return false;
	}
	//******************************************************************************/
	//*********************** INSERT FORECAST OR ANY DATE PAST/FUTURE *************/
	//*****************************************************************************/
	public Boolean insertForecast(Weather w) {
		
		String sql = "INSERT INTO weathers(id_town, id_atmosphere, id_wind, temp_now,"
				+ "temp_max, temp_min, date_day, id_week_day, id_description) "
				+ "values(?,NULL,NULL,0,?,?,TIMESTAMP(?),DAYOFWEEK(date_day),?)";
		try {
			LocalDateTime date = w.getDateDay();
			Timestamp t = Timestamp.valueOf(date);
			st = DBConnection.getConnection().prepareStatement(sql);
			st.setInt(1, w.getTown().getIdTown());
			//st.setInt(2, w.getAtmosphere().getIdAtmosphere());
			//st.setInt(3, w.getWind().getIdWind());
			//st.setInt(4, w.getTempNow());
			st.setInt(2, w.getTempMax());
			st.setInt(3, w.getTempMin());
			st.setTimestamp(4, t);
			st.setInt(5, w.getWeatherDescription().getIdWeatherDescription());


			if(st.executeUpdate()==1) {
				return true;
			}
			rs = st.getGeneratedKeys();
			while(rs.next()) {
				w.setIdWeather(rs.getInt(1));
			}
		}	
		catch(SQLException e) {
			return false;
		}
		finally {
			closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return false;
	}
	//***********************************************************************************/
	//***************************** UPDATE WEATHER **************************************/
	//***********************************************************************************/
	public Boolean update(Weather w) {
		LocalDateTime date = w.getDateDay();
	
		try {

			st = DBConnection.getConnection().prepareStatement(queryGen("weathers","update"));
			Timestamp t = Timestamp.valueOf(date);
			st.setInt(1, w.getTown().getIdTown());
			st.setInt(2, w.getAtmosphere().getIdAtmosphere());
			st.setInt(3, w.getWind().getIdWind());
			st.setInt(4, w.getTempNow());
			st.setInt(5, w.getTempMax());
			st.setInt(6, w.getTempMin());
			st.setTimestamp(7, t); 
			st.setInt(8, w.getWeatherDescription().getIdWeatherDescription());
			st.setInt(9, w.getIdWeather());

			if (st.executeUpdate()==1) {
				return true;
			}

		}
		catch(SQLException e) {
			return false;
		}
		finally {
			//closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return false;
	}
	//*****************************************************************************/
	//***************************** DELETE ****************************************/
	//*****************************************************************************/
	public Boolean delete(Weather w) {
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("weathers","delete"));
			st.setInt(1, w.getIdWeather()); //
			if (st.executeUpdate()==1) {
				return true;
			}
		}
		catch(SQLException e) {
			return false;
		}
		finally {
			//closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return false;
	}
	//*****************************************************************************/
	//*************** Converts RS to WEATHER Object *******************************/
	//*****************************************************************************/
	public Weather convert(ResultSet rs) throws SQLException {
		Town t = new Town();
		t.setIdTown(rs.getInt("id_town"));

		Atmosphere a = new Atmosphere();
		a.setIdAtmosphere(rs.getInt("id_atmosphere"));

		Wind wi = new Wind();
		wi.setIdWind(rs.getInt("id_wind"));

		WeatherDescription wde = new WeatherDescription();
		wde.setIdWeatherDescription(rs.getInt("id_description"));

		int tempNow = rs.getInt("temp_now");
		int tempMax = rs.getInt("temp_max");
		int tempMin = rs.getInt("temp_min");
		Timestamp dateDay = rs.getTimestamp("date_day");
		DayOfWeek weekDay = dateDay.toLocalDateTime().getDayOfWeek();
		
		Weather w = new Weather(t, a, wi, tempNow, tempMax, tempMin, dateDay.toLocalDateTime(), weekDay, wde);
		w.setIdWeather(rs.getInt("id_weather"));
		return w;
	}
	//*******************************************************************************/
	//***************************** SELECT ALL *************************************/
	//*******************************************************************************/
	public List<Weather> selectAll() {
		Weather w = null;
		List<Weather> weathers = new ArrayList<Weather>();
		try {
			//conn = DBConnection.getConnection();
			st = DBConnection.getConnection().prepareStatement(queryGen("weathers","getall"));
			rs = st.executeQuery();
			while(rs.next()) {
				w=convert(rs);
				weathers.add(w);
			}
		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT ALL Weathers" + e.toString());
		}
		finally {
			closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return weathers;
	}
	
	//*******************************************************************************/
	//**************SELECT ALL WEATHERS FROM A STATE AND COUNTRY ********************/
	//*******************************************************************************/
		public List<Weather> selectAllByState(Country c, State s) {
			Weather w = null;
			List<Weather> weathers = new ArrayList<Weather>();
			try {
				String sql = "select id_weather, weathers.id_town, states.id_state, "
						+ "countries.id_country, "
						+ "id_atmosphere, id_wind, temp_now, temp_min, temp_max, "
						+ "id_description, id_week_day, MAX(date_day) AS date_day "
						+ "FROM weathers "
						+ "INNER JOIN towns ON weathers.id_town=towns.id_town "
						+ "INNER JOIN states ON towns.id_state=states.id_state "
						+ "INNER JOIN countries ON states.id_country=countries.id_country "
						+ "WHERE states.id_country=? AND states.id_state=? "
						+ "AND DATE(date_day)=DATE(NOW()) "
						+ "GROUP BY weathers.id_town";

				st = DBConnection.getConnection().prepareStatement(sql);
				st.setInt(1, c.getIdCountry());
				st.setInt(2, s.getIdState());
				
				
				rs = st.executeQuery();
				while(rs.next()) {
					w=convert(rs);
					weathers.add(w);
				}
				if(weathers.size()==0) {
					weathers = null;
				}
				
			}
			catch(SQLException e) {
				System.out.println("Error SQL SELECT ALL Weathers" + e.toString());
			}
			finally {
				closeRS(rs);
				closeSTM(st);
				//closeCON(conn);
			}
			return weathers;
		}
	
	
	//*******************************************************************************/
	//***************************** SELECT ONE *************************************/
	//*******************************************************************************/
	public Weather selectOne(Weather w) {
		try {
			//conn = DBConnection.getConnection();
			st = DBConnection.getConnection().prepareStatement(queryGen("weathers","getone"));
			st.setInt(1, w.getIdWeather());
			rs = st.executeQuery();
			int c = 0;
			if(rs.next()) {
				w=convert(rs);
				c ++;
			}
			if(c==0) {
				w = null;
			}
		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT ONE Weather" + e.toString());
		}
		finally {
			//closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return w;
	}
	//*******************************************************************************/
	//***************************** GET TODAY  **************************************/
	//*******************************************************************************/
	public Weather getToday(Town t) {
		Weather w = null;
		try {
			String sql = "SELECT * FROM weathers WHERE id_town = ? AND DATE(date_day) = CURDATE() "
					+ "ORDER BY (date_day) desc limit 1";
			st = DBConnection.getConnection().prepareStatement(sql);
			st.setInt(1, t.getIdTown());
			rs = st.executeQuery();
			while(rs.next()) {
				w=convert(rs);
			}
		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT Today's Weather" + e.toString());
		}
		finally {
			closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return w;
	}

	//*******************************************************************************/
	//***************************** GET FORECAST *************************************/
	//*******************************************************************************/
		public List<Weather> getForecast(Town t) {
			Weather w = null;
			List<Weather> weathers = new ArrayList<Weather>();
			try {
				String sql = "SELECT * FROM weathers "
							+ "WHERE weathers.id_town=? AND "
							+ "(DATE(date_day) between NOW() and DATE_ADD(NOW(), INTERVAL 10 DAY))";
				st = DBConnection.getConnection().prepareStatement(sql);
				st.setInt(1, t.getIdTown());
				rs = st.executeQuery();
				while(rs.next()) {
					w=convert(rs);
					weathers.add(w);
				}
				if(weathers.size() == 0) {
					weathers = null;
				}
					
			}
			catch(SQLException e) {
				System.out.println("Error SQL SELECT Weather Forecast" + e.toString());
			}
			finally {
				closeRS(rs);
				closeSTM(st);
				//closeCON(conn);
			}
			return weathers;
		}
		//*******************************************************************************/
		//***************************** SELECT ONE BY DATE AND TOWN**********************/
		//*******************************************************************************/
		public Weather selectOnebyDateAndTown(Weather w) {
			Weather weather = new Weather();
			try {
				LocalDateTime date = w.getDateDay();
				Timestamp t = Timestamp.valueOf(date);
				st = DBConnection.getConnection().prepareStatement("SELECT * FROM weathers where "
						+ "id_town=? and date_day=?");
				st.setInt(1, w.getTown().getIdTown());
				st.setTimestamp(2, t);
				rs = st.executeQuery();
				int c = 0;
				if(rs.next()) {
					weather=convert(rs);
					c ++;
				}
				if(c==0) {
					weather = null;
				}
			}
			catch(SQLException e) {
				System.out.println("Error SQL SELECT ONE Weather" + e.toString());
			}
			finally {
				//closeRS(rs);
				closeSTM(st);
				//closeCON(conn);
			}
			return weather;
		}
		
		//*******************************************************************************/
		//***************************** SELECT ONE *************************************/
		//*******************************************************************************/
		public Weather selectLastRegister() {
			Weather w = new Weather();
			try {
				//conn = DBConnection.getConnection();
				st = DBConnection.getConnection().prepareStatement("SELECT * from weathers ORDER BY id_weather desc limit 1");
				rs = st.executeQuery();
				int c = 0;
				if(rs.next()) {
					w=convert(rs);
					c++;
				}
				if(c==0) {
					w = null;
				}
			}
			catch(SQLException e) {
				System.out.println("Error SQL SELECT ONE Weather" + e.toString());
			}
			finally {
				//closeRS(rs);
				closeSTM(st);
				//closeCON(conn);
			}
			return w;
		}
		
}