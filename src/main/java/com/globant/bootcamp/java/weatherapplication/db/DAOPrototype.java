package com.globant.bootcamp.java.weatherapplication.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.globant.bootcamp.java.weatherapplication.connection.DBConnection;
@Component
public class DAOPrototype {
		
	@Autowired
	DBConnection conn;
	PreparedStatement st = null;
	ResultSet rs = null;
	

	//*********************************************************************************************/
	//***************************** GENERATES QUERIES ACORDING TO TABLES **************************/
	//*********************************************************************************************/
	
	String queryGen(String tableName, String queryType) {
		if(queryType.equals("insert")) {
			switch(tableName) {
			case "winds":				return "INSERT INTO winds(speed,direction) VALUES(?,?)";
			case "atmospheres":	 		return "INSERT INTO atmospheres(humidity,pressure,rising,visibility) VALUES(?,?,?,?)";
			case "countries": 			return "INSERT INTO countries(full_name,alpha2_code,alpha3_code) VALUES(?,?,?)";
			case "states": 				return "INSERT INTO states(id_country,full_name,alpha2_code,area,largest_city,capital_city) VALUES(?,?,?,?,?,?)";
			case "towns": 				return "INSERT INTO towns(id_state,full_name) VALUES(?,?)";
			case "weather_descriptions":return "INSERT INTO weather_descriptions(text) VALUES(?)";
			case "weathers": 			return "INSERT INTO weathers(id_town, id_atmosphere, id_wind, temp_now,"
										+ "temp_max, temp_min, date_day, id_week_day, id_description) "
										+ "values(?,?,?,?,?,?,TIMESTAMP(?),DAYOFWEEK(date_day),?)";
			default: return null;
			}	
		}
		else if(queryType.equals("update")) {
			switch(tableName) {
			case "winds":				return "UPDATE winds SET speed = ?, direction = ? WHERE id_wind = ?";
			case "atmospheres": 		return "UPDATE atmospheres SET humidity = ?, pressure = ?, rising = ?, visibility = ? WHERE id_atmosphere = ?";
			case "countries": 			return "UPDATE countries SET full_name = ?, alpha2_code = ?, alpha3_code = ? WHERE id_country = ?";
			case "states": 				return "UPDATE states SET id_country = ?, full_name = ?, alpha2_code = ?, area = ?, largest_city = ?, capital_city = ?  WHERE id_state = ?";
			case "towns": 				return "UPDATE towns SET id_state = ?, full_name = ? WHERE id_town=?";
			case "weather_descriptions":return "UPDATE weather_descriptions SET text = ? WHERE id_description = ?";
			case "weathers": 			return "UPDATE weathers SET id_town=?, id_atmosphere=?, id_wind=?, "
										+ "temp_now=?, temp_max=?, temp_min=?,"
										+ "date_day=TIMESTAMP(?), "
										+ "id_week_day=DAYOFWEEK(date_day), id_description=? WHERE id_weather = ? ";
			default: return null;
			}	
		}
		else if (queryType.equals("delete")) {
			return "DELETE FROM " + tableName + " WHERE " + getPK(tableName) + "=?";
		}
		else if(queryType.equals("getall")) {
			return "SELECT * FROM " + tableName;
		}
		else if(queryType.equals("getone")) {	
			return "SELECT * FROM " + tableName + " WHERE " + getPK(tableName) + "=?";
		}
		return null;
	}

	String getPK(String tableName) {
		switch(tableName) {
		case "winds":			return "id_wind";
		case "atmospheres": 	return "id_atmosphere";
		case "countries": 		return "id_country";
		case "states": 			return "id_state";
		case "towns": 			return "id_town";
		case "weather_descriptions": return "id_description";
		case "weathers": 		return "id_weather";
		default: return null;
		}	
	}


	//*****************************************************************************/
	//**************** METHODS TO CLOSE THE RS, ST, and CONN **********************/
	//*****************************************************************************/
	void closeRS(ResultSet rs) {
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	void closeSTM(PreparedStatement st) {
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	void closeCON(Connection conn) {
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
