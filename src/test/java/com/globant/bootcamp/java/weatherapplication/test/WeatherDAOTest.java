package com.globant.bootcamp.java.weatherapplication.test;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.globant.bootcamp.java.weatherapplication.builders.WeatherBuilder;
import com.globant.bootcamp.java.weatherapplication.db.WeatherDAO;
import com.globant.bootcamp.java.weatherapplication.model.Weather;


@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class WeatherDAOTest {
	

	private static EmbeddedDatabase db;
	@Autowired
	WeatherDAO wd;
	Connection conn = null;

	public static void dataSource() {
		db = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("weatherdb.sql")
				.addScript("insert-data.sql")
				.build();
	}
	@BeforeClass
	public static void setUp() {
		dataSource();
	}
	
	@Test
	public void testGetOneById() {
		//select the first register in the db
		Weather w = new WeatherBuilder().setIdWeather(1).build();
		Weather weather = wd.selectOne(w);
		//set the local date time of the register to compare it
		LocalDateTime dt = LocalDateTime.of(2017,Month.OCTOBER,24,0,8,48);
				
		assertNotNull(weather);
		assertEquals(2, weather.getTown().getIdTown());
		assertEquals(1, weather.getAtmosphere().getIdAtmosphere());
		assertEquals(1, weather.getWind().getIdWind());
		assertTrue(dt.isEqual(weather.getDateDay()));
		assertEquals(dt.getDayOfWeek(), weather.getWeekDay());
		assertEquals(4, weather.getWeatherDescription().getIdWeatherDescription());
		assertEquals(10, weather.getTempNow());
		assertEquals(15, weather.getTempMax());
		assertEquals(5, weather.getTempMin());
		
	}

	@Test
	public void testGetOneByIdFail() {

		Weather w = new Weather();
		w.setIdWeather(800);
		Weather weather = wd.selectOne(w);

		assertNull(weather);
	}

	@Test
	public void testSelectAll() {
		List <Weather> wthrs = wd.selectAll();
		assertNotNull(wthrs);
	}

	@Test
	public void testDelete() {
		
		List <Weather> wthrs1 = wd.selectAll();
		
		Weather last1 = wthrs1.get(wthrs1.size()-1); //last register is id=13
			
		wd.delete(last1); 
				
		List<Weather> wthrs2 = wd.selectAll();
		Weather last2 = wthrs2.get(wthrs2.size()-1);
			
		assertThat(wthrs1, not(wthrs2));
		assertThat(13, not(last2.getIdWeather()));
		assertThat(last1, not(last2));
	}
	
	
	
	/*CANT TEST INSERT OR UPDATES, because H2 is having a problem with the
	 * query where date_day (which is a datetime column) cant be found
	 * it definetely recognizes and loads the column, as it is shown in the
	 * getAll and selectOneById tests, but when i try to insert or update that
	 * column it throws an error: Error SQL INSERT WeatherDAO Weather Today 
	 * org.h2.jdbc.JdbcSQLException: Columna "DATE_DAY" no encontrada
		Column "DATE_DAY" not found
		Also Timestamp() and DATE() arent recognized by the H2 database. 
		I've researched in the
		documentation, and i cant find a way of solving this issues as of now*/
	

	@AfterClass
	public static void tearDown() {
		db.shutdown();
	}

}
