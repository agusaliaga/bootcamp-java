package com.globant.bootcamp.java.weatherapplication.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.globant.bootcamp.java.weatherapplication.connection.DBConnection;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.model.State;

public class StateDAO extends DAOPrototype implements DAO<State> {
	
	@Autowired
	private CountryDAO cd;

	//*****************************************************************************/
	//***************************** INSERT ****************************************/
	//*****************************************************************************/
	public Boolean insert(State s) {
		try {

			st = DBConnection.getConnection().prepareStatement(queryGen("states", "insert"));
			st.setInt(1, s.getCountry().getIdCountry());
			st.setString(2, s.getFullName());
			st.setString(3, s.getAlpha2Code());
			st.setString(4, s.getArea());
			st.setString(5, s.getLargestCity());
			st.setString(6, s.getCapitalCity());

			if(st.executeUpdate()==1) {
				System.out.println("The Register was Inserted");
				return true;
			}
			rs = st.getGeneratedKeys();
			while(rs.next()) {
				s.setIdState(rs.getInt(1));
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
	public Boolean update(State s) {
		try {

			st = DBConnection.getConnection().prepareStatement(queryGen("states", "update"));
			st.setInt(1, s.getCountry().getIdCountry());
			st.setString(2, s.getFullName());
			st.setString(3, s.getAlpha2Code());
			st.setString(4, s.getArea());
			st.setString(5, s.getLargestCity());
			st.setString(6, s.getCapitalCity());
			st.setInt(7, s.getIdState());

			if (st.executeUpdate()==1) {
				System.out.println("The register was updated");
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error UPDATING State" + e.toString());
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
	public Boolean delete(State s) {
		try {

			st = DBConnection.getConnection().prepareStatement(queryGen("states", "delete"));
			st.setInt(1, s.getIdState()); //
			if (st.executeUpdate()==1) {
				System.out.println("The register was deleted");
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error DELETING State" + e.toString());
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
	//******************** Converts RS to State Object ****************************/
	//*****************************************************************************/
	public State convert(ResultSet rs) throws SQLException {
		Country c = new Country();	
		c.setIdCountry(rs.getInt("id_country"));

		State s = new State();
		s.setCountry(c);
		s.setFullName(rs.getString("full_name"));
		s.setAlpha2Code(rs.getString("alpha2_code"));
		s.setArea(rs.getString("area"));
		s.setLargestCity(rs.getString("largest_city"));
		s.setCapitalCity(rs.getString("capital_city"));
		s.setIdState(rs.getInt("id_state"));
		return s;
	}
	//***********************************************************************************************************/
	//******************************************** SELECT ALL ***************************************************/
	//***********************************************************************************************************/
	public List<State> selectAll() {
		State s = null;
		List<State> states = new ArrayList<State>();
		try {
			st = DBConnection.getConnection().prepareStatement(queryGen("states", "getall"));
			rs = st.executeQuery();
			while(rs.next()) {
				s=convert(rs);
				states.add(s);
			}
		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT State " + e.toString());
		}
		finally {
			closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return states;
	}
	//***********************************************************************************************************/
	//******************************************** SELECT ONE ***************************************************/
	//***********************************************************************************************************/
	public State selectOne(State s) {
		State state = new State();
		try {
			st = DBConnection.getConnection().prepareStatement(queryGen("states", "getone"));
			st.setInt(1, s.getIdState());
			rs = st.executeQuery();
			if(rs.next()) {
				state=convert(rs);
			}
			else {
				state=null;
			}
		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT State " + e.toString());
		}
		finally {
			closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return state;
	}
	//********************************************************************************************/
	//***************************** SELECT ONE BY NAME  ******************************************/
	//********************************************************************************************/
	public State selectOneByName(State s) {
		State state = new State();
		try {

			st = DBConnection.getConnection().prepareStatement("SELECT * FROM states WHERE "
					+ "full_name=?");
			st.setString(1, s.getFullName());
			rs = st.executeQuery();
			if(rs.next()) {
				state=convert(rs);
			}
			else {
				state = null;
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
		return state;
	}

	//********************************************************************************************/
	//***************************** SELECT ONE BY SHORT CODE  ************************************/
	//********************************************************************************************/
	public State selectOneByShortCode(State s) {
		State state = new State();
		try {

			st = DBConnection.getConnection().prepareStatement("SELECT * FROM states WHERE "
					+ "alpha2_code=?");
			st.setString(1, s.getAlpha2Code());
			rs = st.executeQuery();
			if(rs.next()) {
				state=convert(rs);
			}
			else {
				state = null;
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
		return state;
	}

	//********************************************************************************************/
	//********************* SELECT STATE BY COUNTRY & SHORT CODE  ********************************/
	//********************************************************************************************/
	public State selectOneByCountryAndShortCode(State s, Country c) {
		State state = new State();
		Country country = new Country();
		try {
			country = cd.selectOneByLongCode(c);
		
			st = DBConnection.getConnection().prepareStatement("SELECT * FROM states WHERE "
					+ "alpha2_code=? AND id_country=?");
			st.setString(1, s.getAlpha2Code());
			st.setInt(2, country.getIdCountry());
			rs = st.executeQuery();
			if(rs.next()) {
				state=convert(rs);
			}
			else {
				state = null;
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
		return state;
	}


	//********************************************************************************************/
	//********************* SELECT STATE BY COUNTRY **********************************************/
	//********************************************************************************************/
	public List<State> selectOneByCountry(Country c) {
		State state = null;
		List<State> states = new ArrayList<State>();
		try {

			st = DBConnection.getConnection().prepareStatement("SELECT * FROM states WHERE "
					+ "id_country=?");
			st.setInt(1, c.getIdCountry());
			rs = st.executeQuery();
			while(rs.next()) {
				state=convert(rs);
				states.add(state);
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
		return states;
	}
}
