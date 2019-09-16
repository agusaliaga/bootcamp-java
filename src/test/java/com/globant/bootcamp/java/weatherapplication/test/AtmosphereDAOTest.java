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

import com.globant.bootcamp.java.weatherapplication.builders.AtmosphereBuilder;
import com.globant.bootcamp.java.weatherapplication.db.AtmosphereDAO;
import com.globant.bootcamp.java.weatherapplication.model.Atmosphere;


@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AtmosphereDAOTest {


	private static EmbeddedDatabase db;
	@Autowired
	AtmosphereDAO ad;
	Connection conn = null;
	private static final double DELTA = 1e-2;
	
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

		Atmosphere a = new AtmosphereBuilder().setIdAtmosphere(1).build();
		Atmosphere atm = ad.selectOne(a);

		assertNotNull(atm);
		assertEquals(1, atm.getIdAtmosphere());
		assertEquals(80, atm.getHumidity());
		assertEquals(1028, atm.getPressure(),DELTA);
		assertEquals(0, atm.getRising());
		assertEquals(16.1, atm.getVisibility(),DELTA);
	}

	@Test
	public void testGetOneByIdFail() {

		Atmosphere a = new Atmosphere();
		a.setIdAtmosphere(800);
		Atmosphere atm = ad.selectOne(a);

		//if it doesnt find, the attributes are set to their default value
		assertEquals(0, atm.getHumidity());
		assertEquals(0, atm.getPressure(), DELTA);
		assertEquals(0, atm.getRising());
		assertEquals(0, atm.getVisibility(), DELTA);
	}

	@Test
	public void testSelectAll() {
		List <Atmosphere> atms = ad.selectAll();
		assertNotNull(atms);
	}

	@Test
	public void testInsertAtmosphere() {

		Atmosphere a = new AtmosphereBuilder().build();
		ad.insert(a);
		List <Atmosphere> atms = ad.selectAll();

		int last = atms.get(atms.size()-1).getIdAtmosphere()-1;
		int beforelast = atms.get(atms.size()-2).getIdAtmosphere();

		
		assertEquals(last, beforelast);
		assertEquals(50, atms.get(atms.size()-1).getHumidity());
		assertEquals(1120, atms.get(atms.size()-1).getPressure(),DELTA);
		assertEquals(2, atms.get(atms.size()-1).getRising());
		assertEquals(15, atms.get(atms.size()-1).getVisibility(),DELTA);

	}
	
	@Test
	public void testUpdate() {
		Atmosphere a = new AtmosphereBuilder().build();
		ad.update(a);
		Atmosphere atm = ad.selectOne(a);

		assertEquals(3, atm.getIdAtmosphere());
		assertEquals(50, atm.getHumidity());
		assertEquals(1120, atm.getPressure(),DELTA);
		assertEquals(2, atm.getRising());
		assertEquals(15, atm.getVisibility(),DELTA);
	}

	@Test
	public void testUpdateFail() {
		Atmosphere a = new Atmosphere();
		a.setIdAtmosphere(800);
		ad.update(a);
		Atmosphere atm = ad.selectOne(a);

		assertEquals(0, atm.getHumidity());
		assertEquals(0, atm.getPressure(),DELTA);	
		assertEquals(0, atm.getRising());	
		assertEquals(0, atm.getVisibility(),DELTA);	
	}

	@Test
	public void testDelete() {
		//insert an atmosphere at the end
		Atmosphere atmosphere = new AtmosphereBuilder().build();
		ad.insert(atmosphere);
		//make a list with all atmospheres
		List <Atmosphere> atms1 = ad.selectAll();
		//delete the atmosphere i just created, which will be id = 5
		Atmosphere a = new Atmosphere();
		a.setIdAtmosphere(5);
		ad.delete(a);
		//make a new list of atmospheres
		List <Atmosphere> atms2 = ad.selectAll();
		//select the atmosphere id = 5 that doesnt exist, so the values will be set to default
		ad.selectOne(a);

		assertThat(5, not(atms2.get(4).getIdAtmosphere()));
		assertThat(atms1, not(atms2));
		assertEquals(0, a.getHumidity());
		assertEquals(0, a.getPressure(),DELTA);	
		assertEquals(0, a.getRising());	
		assertEquals(0, a.getVisibility(),DELTA);	
	}
	
	
	@AfterClass
	public static void tearDown() {
		db.shutdown();
	}

}
