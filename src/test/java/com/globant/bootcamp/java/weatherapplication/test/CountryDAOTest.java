package com.globant.bootcamp.java.weatherapplication.test;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

import com.globant.bootcamp.java.weatherapplication.builders.CountryBuilder;
import com.globant.bootcamp.java.weatherapplication.db.CountryDAO;
import com.globant.bootcamp.java.weatherapplication.model.Country;

@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CountryDAOTest {
	private static EmbeddedDatabase db;
	@Autowired
	CountryDAO cd;
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

		Country c = new CountryBuilder().setIdCountry(1).build();
		Country ctry = cd.selectOne(c);

		assertNotNull(ctry);
		assertEquals(1, ctry.getIdCountry());
		assertEquals("India", ctry.getFullName());
		assertEquals("IN", ctry.getAlpha2Code());
		assertEquals("IND", ctry.getAlpha3Code());
	}

	@Test
	public void testGetOneByIdFail() {

		Country c = new Country();
		c.setIdCountry(800);
		Country ctry = cd.selectOne(c);

		//if it doesnt find the country, the attributes are set to their default value
		assertNull(ctry);
	}

	@Test
	public void testSelectAll() {
		List <Country> ctrs = cd.selectAll();
		assertNotNull(ctrs);
	}

	@Test
	public void testInsertCountry() {
		
		List <Country> ctrs1 = cd.selectAll();

		Country c = new CountryBuilder().build();
		cd.insert(c);
		
		List <Country> ctrs2 = cd.selectAll();

		int last = ctrs2.get(ctrs2.size()-1).getIdCountry()-1;
		int beforelast = ctrs2.get(ctrs2.size()-2).getIdCountry();
		
		assertNotNull(cd.selectOne(c));
		assertEquals(last, beforelast);
		assertThat(ctrs1.size(), not(ctrs2.size()));
		assertEquals("Chile", ctrs2.get(ctrs2.size()-1).getFullName());
		assertEquals("CH", ctrs2.get(ctrs2.size()-1).getAlpha2Code());
		assertEquals("CHL", ctrs2.get(ctrs2.size()-1).getAlpha3Code());

	}
	
	@Test
	public void testUpdate() {
		Country c = new CountryBuilder().build(); // builds ctry with id=1
		cd.update(c);
		Country ctry = cd.selectOne(c);

		assertEquals(1, ctry.getIdCountry());
		assertEquals("Chile", ctry.getFullName());
		assertEquals("CH", ctry.getAlpha2Code());
		assertEquals("CHL", ctry.getAlpha3Code());
	}

	@Test
	public void testUpdateFail() {
		Country c = new Country();
		c.setIdCountry(800);
		cd.update(c);
		Country ctry = cd.selectOne(c);
		
		assertNull(ctry);
		
	}

	@Test
	public void testDelete() {
		//insert an atmosphere at the end
		Country country = new CountryBuilder().build();
		cd.insert(country);
		//make a list with all atmospheres
		List <Country> ctrs1 = cd.selectAll();
		//delete the atmosphere i just created, which will be id = 5
		Country c = new Country();
		c.setIdCountry(4);
		cd.delete(c);
		//make a new list of atmospheres
		List <Country> ctrs2 = cd.selectAll();
		//select the country id = 4 that doesnt exist, so the values will be set to default
		cd.selectOne(c);

		assertThat(4, not(ctrs2.get(3).getIdCountry()));
		assertThat(ctrs1, not(ctrs2));
		assertNull(c.getFullName());
		assertNull(c.getAlpha2Code());
		assertNull(c.getAlpha3Code());
	}
	
	
	@AfterClass
	public static void tearDown() {
		db.shutdown();
	}


}
