package com.globant.bootcamp.java.weatherapplication.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.globant.bootcamp.java.weatherapplication.connection.DBConnection;
import com.globant.bootcamp.java.weatherapplication.model.Country;

public class CountryDAO extends DAOPrototype implements DAO<Country> {
	
	//*****************************************************************************/
	//***************************** INSERT ****************************************/
	//*****************************************************************************/
	public Boolean insert(Country c) {
		try {
		
			st = DBConnection.getConnection().prepareStatement(queryGen("countries", "insert"));
			
			st.setString(1, c.getFullName());
			st.setString(2, c.getAlpha2Code());
			st.setString(3, c.getAlpha3Code());
					
			if(st.executeUpdate()==1) {
				return true;
			}
			rs = st.getGeneratedKeys();
			while(rs.next()) {
				c.setIdCountry(rs.getInt(1));
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
	//*****************************************************************************/
	//***************************** UPDATE ****************************************/
	//*****************************************************************************/
	public Boolean update(Country c) {
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("countries", "update"));
			st.setString(1, c.getFullName());
			st.setString(2, c.getAlpha2Code());
			st.setString(3, c.getAlpha3Code());
			st.setInt(4, c.getIdCountry()); //where id=atmosphere id
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
	public Boolean delete(Country c) {
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("countries", "delete"));
			st.setInt(1, c.getIdCountry()); //where id=atmosphere id
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
	//****************** Converts RS to Country Object ****************************/
	//*****************************************************************************/
	public Country convert(ResultSet rs) throws SQLException {
		int idcountry = (rs.getInt("id_country"));
		String fullName = rs.getString("full_name");
		String alpha2Code = rs.getString("alpha2_code");
		String alpha3Code = rs.getString("alpha3_code");
		Country c = new Country(fullName,alpha2Code,alpha3Code);
		c.setIdCountry(idcountry);
		return c;
	}	
	//*****************************************************************************/
	//***************************** SELECT ALL ************************************/
	//*****************************************************************************/
	public List<Country> selectAll() {
		Country c = null;
		List<Country> countries = new ArrayList<Country>();
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("countries", "getall"));
			rs = st.executeQuery();
			while(rs.next()) {
				c=convert(rs);
				countries.add(c);
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
		return countries;
	}
	//******************************************************************************/
	//***************************** SELECT ONE *************************************/
	//******************************************************************************/
	public Country selectOne(Country c) {
		Country country = new Country();
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("countries", "getone"));
			st.setInt(1, c.getIdCountry());
			rs = st.executeQuery();
			if(rs.next()) {
				country=convert(rs);
			}
			else {
				country=null;
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
		return country;
	}
	//********************************************************************************************/
	//***************************** SELECT ONE BY SHORT CODE *************************************/
	//********************************************************************************************/
	public Country selectOneByShortCode(Country c) {
		Country country = new Country();
		try {
			
			st = DBConnection.getConnection().prepareStatement("SELECT * FROM countries WHERE "
					+ "alpha2_code=?");
			st.setString(1, c.getAlpha2Code());
			rs = st.executeQuery();
			
			if(rs.next()) {
				country=convert(rs);
			}
			else {
				country = null;
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
		return country;
	}
	//********************************************************************************************/
	//***************************** SELECT ONE BY LONG CODE **************************************/
	//********************************************************************************************/
	public Country selectOneByLongCode(Country c) {
		Country country = new Country();
		try {
			
			st = DBConnection.getConnection().prepareStatement("SELECT * FROM countries WHERE "
					+ "alpha3_code=?");
			st.setString(1, c.getAlpha3Code());
			rs = st.executeQuery();
			if(rs.next()) {
				country=convert(rs);
			}
			else {
				country = null;
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
		return country;
	}
	
	//********************************************************************************************/
	//***************************** SELECT ONE BY NAME  ******************************************/
	//********************************************************************************************/
	public Country selectOneByName(Country c) {
		Country country = new Country();
		try {
			
			st = DBConnection.getConnection().prepareStatement("SELECT * FROM countries WHERE "
					+ "full_name=?");
			st.setString(1, c.getFullName());
			rs = st.executeQuery();
			if(rs.next()) {
				country=convert(rs);
			}
			else {
				country = null;
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
		return country;
	}
	
	
}
