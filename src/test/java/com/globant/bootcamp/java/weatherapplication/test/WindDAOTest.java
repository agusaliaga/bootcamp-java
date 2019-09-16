package com.globant.bootcamp.java.weatherapplication.test;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
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

import com.globant.bootcamp.java.weatherapplication.builders.WindBuilder;

import com.globant.bootcamp.java.weatherapplication.db.WindDAO;
import com.globant.bootcamp.java.weatherapplication.model.Wind;

@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class WindDAOTest {

	private static EmbeddedDatabase db;
	@Autowired
	WindDAO wd;
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

		Wind w = new WindBuilder().setIdWind(1).build();
		Wind wind = wd.selectOne(w);

		assertNotNull(wind);
		assertEquals(1, wind.getIdWind());
		assertEquals(113, wind.getSpeed());
		assertEquals(13, wind.getDirection());
	}
	
	@Test
	public void testGetOneByIdFail() {

		Wind w = new Wind();
		w.setIdWind(800);
		Wind wind = wd.selectOne(w);
		
		//if it doesnt find, the attributes are set to their default value
		assertEquals(0, wind.getDirection());
		assertEquals(0, wind.getSpeed());
	}

	@Test
	public void testSelectAll() {
		List <Wind> winds = wd.selectAll();
		assertNotNull(winds);
	}
	
	@Test
	public void testInsertWind() {
		
		Wind w = new WindBuilder().build();
		wd.insert(w);
		List <Wind> winds = wd.selectAll();
		
		int last = winds.get(winds.size()-1).getIdWind()-1;
		int beforelast = winds.get(winds.size()-2).getIdWind();
		
		/*auto increment for id column working*/
		assertEquals(last, beforelast);
		assertEquals(20, winds.get(winds.size()-1).getSpeed());
		assertEquals(15, winds.get(winds.size()-1).getDirection());
		
	}
		
	@Test
	public void testUpdate() {
		Wind w = new WindBuilder().build();
		wd.update(w);
		Wind wind = wd.selectOne(w);
		
		assertEquals(1, wind.getIdWind());
		assertEquals(20, wind.getSpeed());
		assertEquals(15, wind.getDirection());
	}
	
	@Test
	public void testUpdateFail() {
		Wind w = new Wind();
		w.setIdWind(800);
		wd.update(w);
		Wind wind = wd.selectOne(w);
		
		assertEquals(0, wind.getSpeed());
		assertEquals(0, wind.getDirection());	
	}
	
	@Test
	public void testDelete() {
		Wind w = new Wind();
		w.setIdWind(10);
		wd.delete(w);
		List <Wind> winds = wd.selectAll();
		Wind wind = wd.selectOne(w);
		
		assertThat(10, not(winds.get(9).getIdWind()));
		assertEquals(0, wind.getSpeed());
		assertEquals(0, wind.getDirection());
		
	}
	
	@AfterClass
	public static void tearDown() {
		db.shutdown();
	}

}
