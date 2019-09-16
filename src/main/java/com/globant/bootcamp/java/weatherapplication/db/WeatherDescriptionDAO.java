package com.globant.bootcamp.java.weatherapplication.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.globant.bootcamp.java.weatherapplication.connection.DBConnection;
import com.globant.bootcamp.java.weatherapplication.model.WeatherDescription;

public class WeatherDescriptionDAO extends DAOPrototype implements DAO <WeatherDescription>{
	
	//*****************************************************************************/
	//***************************** INSERT ****************************************/
	//*****************************************************************************/
	public Boolean insert(WeatherDescription wd) {
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("weather_descriptions","insert"));
			st.setString(1, wd.getText());
			if(st.executeUpdate()==1) {
				System.out.println("The Register was Inserted");
				return true;
			}
			rs = st.getGeneratedKeys();
			while(rs.next()) {
				wd.setIdWeatherDescription(rs.getInt(1));
			}
		}	
		catch(SQLException e) {
			System.out.println("Error SQL INSERT Weather Description" + e.toString());
			return false;
		}
		finally {
			closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return false;
	}
	//*****************************************************************************/
	//***************************** UPDATE ****************************************/
	//*****************************************************************************/

	public Boolean update(WeatherDescription wd) {
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("weather_descriptions","update"));
			st.setString(1, wd.getText());
			st.setInt(2, wd.getIdWeatherDescription()); 
			if (st.executeUpdate()==1) {
				System.out.println("The register was updated");
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error UPDATING Weather Description" + e.toString());
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
	public Boolean delete(WeatherDescription wd) {
		try {
			//conn = DBConnection.getConnection();
			st = DBConnection.getConnection().prepareStatement(queryGen("weather_descriptions","delete"));
			st.setInt(1, wd.getIdWeatherDescription()); 
			if (st.executeUpdate()==1) {
				System.out.println("The register was deleted");
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error DELETING Weather Description" + e.toString());
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
	//****************** Converts RS to Country Object ****************************/
	//*****************************************************************************/
	public WeatherDescription convert(ResultSet rs) throws SQLException {
		String text = rs.getString("text");
		WeatherDescription wd = new WeatherDescription(text);
		wd.setIdWeatherDescription(rs.getInt("id_description"));
		return wd;
	}	

	//*****************************************************************************/
	//***************************** SELECT ALL ************************************/
	//*****************************************************************************/
	public List<WeatherDescription> selectAll() {
		WeatherDescription wd = null;
		List<WeatherDescription> wds = new ArrayList<WeatherDescription>();
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("weather_descriptions","getall"));
			rs = st.executeQuery();
			while(rs.next()) {
				wd=convert(rs);
				wds.add(wd);
			}
		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT Weather Description " + e.toString());
		}
		finally {
			closeRS(rs);
			closeSTM(st);
		}
		//closeCON(conn);
		return wds;
	}

	//******************************************************************************/
	//***************************** SELECT ONE *************************************/
	//******************************************************************************/
	public WeatherDescription selectOne(WeatherDescription wd) {
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("weather_descriptions","getone"));
			st.setInt(1, wd.getIdWeatherDescription());
			rs = st.executeQuery();
			if(rs.next()) {
				wd=convert(rs);
			}
		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT Weather Description " + e.toString());
		}
		finally {
			closeRS(rs);
			closeSTM(st);
		}
	//	closeCON(conn);
		return wd;
	}
	
	//********************************************************************************************/
	//***************************** SELECT ONE BY NAME  ******************************************/
	//********************************************************************************************/
	public WeatherDescription selectOneByName(WeatherDescription wdscr) {
		WeatherDescription wdesc = new WeatherDescription();
		try {

			st = DBConnection.getConnection().prepareStatement("SELECT * FROM weather_descriptions WHERE "
					+ "text=?");
			st.setString(1, wdscr.getText());
			rs = st.executeQuery();
			if(rs.next()) {
				wdesc=convert(rs);
			}
			else {
				wdesc = null;
			}

		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT " + e.toString());
		}
		finally {
			closeRS(rs);
			closeSTM(st);
		}
		//closeCON(conn);
		return wdesc;
	}
}
