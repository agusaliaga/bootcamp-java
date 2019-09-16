package com.globant.bootcamp.java.weatherapplication.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.globant.bootcamp.java.weatherapplication.connection.DBConnection;
import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.model.Town;

public class TownDAO extends DAOPrototype implements DAO<Town>{

	
	
	//*****************************************************************************/
	//***************************** INSERT ****************************************/
	//*****************************************************************************/
	@Override
	public Boolean insert(Town t) {
		try {
		
			st = DBConnection.getConnection().prepareStatement(queryGen("towns", "insert"));
			st.setInt(1, t.getState().getIdState());
			st.setString(2, t.getFullName());
								
			if(st.executeUpdate()==1) {
				System.out.println("The Register was Inserted");
				return true;
			}
			rs = st.getGeneratedKeys();
			while(rs.next()) {
				t.setIdTown(rs.getInt(1));
			}
		}	
		catch(SQLException e) {
				System.out.println("Error SQL INSERT Town " + e.toString());
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
	
	@Override
	public Boolean update(Town t) {
		try {
			//conn = DBConnection.getConnection();
			st = DBConnection.getConnection().prepareStatement(queryGen("towns", "update"));
			st.setInt(1, t.getState().getIdState());
			st.setString(2, t.getFullName());
			st.setInt(3, t.getIdTown());
		
			if (st.executeUpdate()==1) {
				System.out.println("The register was updated");
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error UPDATING Town " + e.toString());
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
	@Override
	public Boolean delete(Town t) {
		try {
			//conn = DBConnection.getConnection();
			st = DBConnection.getConnection().prepareStatement(queryGen("towns", "delete"));
			st.setInt(1, t.getIdTown()); //
			if (st.executeUpdate()==1) {
				System.out.println("The register was deleted");
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error DELETING Town" + e.toString());
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
	//******************** Converts RS to Town Object ****************************/
	//*****************************************************************************/
	public Town convert(ResultSet rs) throws SQLException {
		State s = new State();	
		s.setIdState(rs.getInt("id_state"));

		String fullName = rs.getString("full_name");
		
		Town t = new Town(s, fullName);
		t.setIdTown(rs.getInt("id_town"));
		return t;
	}
	//***********************************************************************************************************/
	//******************************************** SELECT ALL ***************************************************/
	//***********************************************************************************************************/
	@Override
	public List<Town> selectAll() {
		Town t = null;
		List<Town> towns = new ArrayList<Town>();
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("towns", "getall"));
			rs = st.executeQuery();
			while(rs.next()) {
				t=convert(rs);
				towns.add(t);
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
		return towns;
	}
	
	//***********************************************************************************************************/
	//******************************************** SELECT ONE ***************************************************/
	//***********************************************************************************************************/

	@Override
	public Town selectOne(Town t) {
		// TODO Auto-generated method stub
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("towns", "getone"));
			st.setInt(1, t.getIdTown());
			rs = st.executeQuery();
			if(rs.next()) {
				t=convert(rs);
			}
		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT Town " + e.toString());
		}
		finally {
			closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return t;
	}
	
	//***********************************************************************************************************/
	//******************************************** SELECT ONE BY NAME *******************************************/
	//***********************************************************************************************************/

	
	public Town selectOneByName(Town t) {
		Town town = null;
		try {

			st = DBConnection.getConnection().prepareStatement("SELECT * FROM towns WHERE "
					+ "full_name=?");
			st.setString(1, t.getFullName());
			rs = st.executeQuery();
			if(rs.next()) {
				town=convert(rs);
			}
			else {
				town = null;
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
		return town;
	}
}
