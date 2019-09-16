package com.globant.bootcamp.java.weatherapplication.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.globant.bootcamp.java.weatherapplication.connection.DBConnection;
import com.globant.bootcamp.java.weatherapplication.model.Wind;
@Component
public class WindDAO extends DAOPrototype implements DAO<Wind> {
		
	//*****************************************************************************/
	//***************************** INSERT ****************************************/
	//*****************************************************************************/
	public Boolean insert(Wind w) {
		
		try {
			st = DBConnection.getConnection().prepareStatement(queryGen("winds","insert"));
			st.setInt(1, w.getSpeed());
			st.setFloat(2, w.getDirection());

			if(st.executeUpdate()==1) {
				System.out.println("The Register was Inserted");
				return true;
			}
			rs = st.getGeneratedKeys();
			while(rs.next()) {
				w.setIdWind(rs.getInt(1));
			}
		}	
		catch(SQLException e) {
			System.out.println("Error SQL INSERT Wind " + e.toString());
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
	public Boolean update(Wind w) {
		
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("winds","update"));
			st.setInt(1, w.getSpeed());
			st.setFloat(2, w.getDirection());
			st.setInt(3, w.getIdWind()); //where id=atmosphere id
			if (st.executeUpdate()==1) {
				System.out.println("The register was updated");
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error UPDATING Wind" + e.toString());
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
	public Boolean delete(Wind w) {

		try {
			st = DBConnection.getConnection().prepareStatement(queryGen("winds","delete"));
			st.setInt(1, w.getIdWind()); 
			if (st.executeUpdate()==1) {
				System.out.println("The register was deleted");
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error DELETING Wind" + e.toString());
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
	//********************* Converts RS to Wind Object ****************************/
	//*****************************************************************************/
	public Wind convert(ResultSet rs) throws SQLException {
		
		int speed = rs.getInt("speed");
		int direction = rs.getInt("direction");
		Wind w = new Wind(speed, direction);
		w.setIdWind(rs.getInt("id_wind"));
		return w;
				
	}	
	//*****************************************************************************/
	//***************************** SELECT ALL ************************************/
	//*****************************************************************************/
	public List<Wind> selectAll() {
		
		Wind w = null;
		List<Wind> winds = new ArrayList<Wind>();
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("winds","getall"));
			rs = st.executeQuery();
			while(rs.next()) {
				w=convert(rs);
				winds.add(w);
			}
		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT Wind " + e.toString());
		}
		finally {
			closeRS(rs);
			closeSTM(st);
		}
		
		return winds;
	}
	//******************************************************************************/
	//***************************** SELECT ONE *************************************/
	//******************************************************************************/
	public Wind selectOne(Wind w) {

		try {
			st = DBConnection.getConnection().prepareStatement(queryGen("winds","getone"));
			st.setInt(1, w.getIdWind());
			rs = st.executeQuery();
			if(rs.next()) {
				w=convert(rs);
			}
		}
		catch(SQLException e) {
			System.out.println("Error SQL SELECT Wind " + e.toString());
		}
		finally {
			closeRS(rs);
			closeSTM(st);
		}
		return w;
	}
	
	
	//*******************************************************************************/
	//***************************** SELECT ONE BY SPEED DIRECTION *******************/
	//*******************************************************************************/
		public Wind selectOneBySpeedAndDirection(Wind w) {
			Wind wind = new Wind();
			try {
				st = DBConnection.getConnection().prepareStatement("SELECT * FROM winds WHERE speed=? AND direction=?");
				st.setInt(1, w.getSpeed());
				st.setInt(2, w.getDirection());
				rs = st.executeQuery();
				if(rs.next()) {
					wind=convert(rs);
				}
				else {
					wind=null;
				}
			}
			catch(SQLException e) {
				System.out.println("Error SQL SELECT Wind " + e.toString());
			}
			finally {
				closeRS(rs);
				closeSTM(st);
			}
			return wind;
		}
	
}
