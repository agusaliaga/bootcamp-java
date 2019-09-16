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

import com.globant.bootcamp.java.weatherapplication.builders.TownBuilder;
import com.globant.bootcamp.java.weatherapplication.db.TownDAO;
import com.globant.bootcamp.java.weatherapplication.model.Town;

@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TownDAOTest {

	
	private static EmbeddedDatabase db;
	@Autowired
	TownDAO td;
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

		Town t = new TownBuilder().setIdTown(1).build();
		Town twn = td.selectOne(t);

		assertNotNull(twn);
		assertEquals(1, twn.getIdTown());
		assertEquals(1, twn.getState().getIdState());
		assertEquals("Hyderabad Amaravati", twn.getFullName());
		//assertEquals("HA", twn.getAlpha2Code());
		/*assertEquals("Kestopur", twn.getFullName());
		assertEquals("KT", twn.getAlpha2Code());*/
	}

	@Test
	public void testGetOneByIdFail() {

		Town t = new Town();
		t.setIdTown(800);
		Town twn = td.selectOne(t);

		//if it doesnt find the state, the attributes are set to their default value
		assertNull(twn.getState());
		assertNull(twn.getFullName());
		//assertNull(twn.getAlpha2Code());
	}

	@Test
	public void testSelectAll() {
		List <Town> twns = td.selectAll();
		assertNotNull(twns);
	}

	@Test
	public void testInsertTown() {
		
		List <Town> twns1 = td.selectAll();

		Town t = new TownBuilder().build();
		td.insert(t);
		
		List <Town> twns2 = td.selectAll();

		int last = twns2.get(twns2.size()-1).getIdTown()-1;
		int beforelast = twns2.get(twns2.size()-2).getIdTown();
		
		assertNotNull(td.selectOne(t));
		assertEquals(last, beforelast);
		assertThat(twns1.size(), not(twns2.size()));
		assertEquals(1, t.getState().getIdState());
		assertEquals("Kestopur", twns2.get(twns2.size()-1).getFullName());
		//assertEquals("KT", twns2.get(twns2.size()-1).getAlpha2Code());
		
	}
	
	@Test
	public void testUpdate() {
		Town t = new TownBuilder().build(); // builds town with id=1
		td.update(t);
		Town twn = td.selectOne(t);

		assertEquals(1, twn.getIdTown());
		assertEquals(1, twn.getState().getIdState());
		assertEquals("Kestopur", twn.getFullName());
		//assertEquals("KT", twn.getAlpha2Code());
	}

	
	@Test
	public void testDelete() {
		//insert an Town at the end
		Town town = new TownBuilder().build();
		td.insert(town);
		//make a list with all Towns
		List <Town> twns1 = td.selectAll();
		//delete the Town i just created, which will be id = 11
		Town t = new Town();
		t.setIdTown(7);
		td.delete(t);
		//make a new list of Towns
		List <Town> twns2 = td.selectAll();
		//select the state id = 11 that doesnt exist, so the values will be set to default
		td.selectOne(t);

		assertThat(7, not(twns2.get(6).getIdTown()));
		assertThat(twns1, not(twns2));
		
		assertNull(t.getState());
		assertNull(t.getFullName());
		//assertNull(t.getAlpha2Code());
	}

	@AfterClass
	public static void tearDown() {
		db.shutdown();
	}
	
	
	
}
