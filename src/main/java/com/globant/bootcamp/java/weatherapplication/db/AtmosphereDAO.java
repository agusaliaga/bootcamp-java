package com.globant.bootcamp.java.weatherapplication.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.globant.bootcamp.java.weatherapplication.connection.DBConnection;
import com.globant.bootcamp.java.weatherapplication.model.Atmosphere;

public class AtmosphereDAO extends DAOPrototype implements DAO<Atmosphere> {

	//*****************************************************************************/
	//***************************** INSERT ****************************************/
	//*****************************************************************************/
	public Boolean insert(Atmosphere atm){
		try {
			st = DBConnection.getConnection().prepareStatement(queryGen("atmospheres","insert"));
			st.setInt(1, atm.getHumidity());
			st.setDouble(2, atm.getPressure());
			st.setInt(3, atm.getRising());
			st.setDouble(4, atm.getVisibility());
			
			if(st.executeUpdate()==1) {
				System.out.println("The Register was Inserted");
				return true;
			}
			rs = st.getGeneratedKeys();
			while(rs.next()) {
				atm.setIdAtmosphere(rs.getInt(1));
			}
		}	
		catch(SQLException e) {
				System.out.println("Error SQL INSERT Atmosphere " + e.toString());
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
	public Boolean update(Atmosphere atm) {
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("atmospheres","update"));
			st.setInt(1, atm.getHumidity());
			st.setDouble(2, atm.getPressure());
			st.setInt(3, atm.getRising());
			st.setDouble(4, atm.getVisibility());
			st.setInt(5, atm.getIdAtmosphere()); //where id=atmosphere id
			if (st.executeUpdate()==1) {
				System.out.println("The register was Updated");
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error UPDATING Atmosphere" + e.toString());
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
	public Boolean delete(Atmosphere atm) {
		try {
			st = DBConnection.getConnection().prepareStatement(queryGen("atmospheres","delete"));
			st.setInt(1, atm.getIdAtmosphere()); //where id=atmosphere id
			if (st.executeUpdate()==1) {
				System.out.println("The register was deleted");
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error Deleting Atmosphere" + e.toString());
			return false;
		}
		finally {
			if(st!=null) {
				try {
					st.close();
				}
				catch(SQLException e) {
					System.out.println("Error Closing Statement" + e.toString());
				}
			}
			//closeRS(rs);
			closeSTM(st);
			//closeCON(conn);
		}
		return false;
	}
	//*****************************************************************************/
	//*************** Converts RS to Atmosphere Object ****************************/
	//*****************************************************************************/
	public Atmosphere convert(ResultSet rs) throws SQLException {
		int humidity = rs.getInt("humidity");
		double pressure = rs.getDouble("pressure");
		int rising = rs.getInt("rising");
		double visibility = rs.getDouble("visibility");
		Atmosphere atm = new Atmosphere(humidity,pressure,rising,visibility);
		atm.setIdAtmosphere(rs.getInt("id_atmosphere"));
		return atm;
	}	
	//*****************************************************************************/
	//***************************** SELECT ALL ************************************/
	//*****************************************************************************/
	public List<Atmosphere> selectAll() {
		Atmosphere atm = null;
		List<Atmosphere> atms = new ArrayList<Atmosphere>();
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("atmospheres","getall"));
			rs = st.executeQuery();
			while(rs.next()) {
				atm=convert(rs);
				atms.add(atm);
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
		return atms;
	}
	//******************************************************************************/
	//***************************** SELECT ONE *************************************/
	//******************************************************************************/
	public Atmosphere selectOne(Atmosphere atm) {
		try {
			
			st = DBConnection.getConnection().prepareStatement(queryGen("atmospheres","getone"));
			st.setInt(1, atm.getIdAtmosphere());;
			rs = st.executeQuery();
			if(rs.next()) {
				atm=convert(rs);
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
		return atm;
	}
	
	//****************************************************************************************/
	//***************************** SELECT BY ATTRIBUTES *************************************/
	//****************************************************************************************/
		public Atmosphere selectOneByAttributes(Atmosphere atm) {
			Atmosphere atmosphere = new Atmosphere();
			try {
				
				st = DBConnection.getConnection().prepareStatement("SELECT * FROM atmospheres "
						+ "WHERE humidity=? AND pressure=? AND rising=? AND visibility=?");
				st.setInt(1, atm.getHumidity());
				st.setDouble(2, atm.getPressure());
				st.setInt(3, atm.getRising());
				st.setDouble(4, atm.getVisibility());
				rs = st.executeQuery();
				if(rs.next()) {
					atmosphere=convert(rs);
				}
				else {
					atmosphere=null;
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
			return atmosphere;
		}
	
	
}
