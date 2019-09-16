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

import com.globant.bootcamp.java.weatherapplication.builders.WeatherDescriptionBuilder;
import com.globant.bootcamp.java.weatherapplication.db.WeatherDescriptionDAO;
import com.globant.bootcamp.java.weatherapplication.model.WeatherDescription;

@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class WeatherDescriptionDAOTest {

	private static EmbeddedDatabase db;
	@Autowired
	WeatherDescriptionDAO wdd;
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

		WeatherDescription wd = new WeatherDescriptionBuilder().setIdWeatherDescription(1).build();
		WeatherDescription wdesc = wdd.selectOne(wd);

		assertNotNull(wdesc);
		assertEquals(1, wdesc.getIdWeatherDescription());
		assertEquals("Sunny", wdesc.getText());

	}

	@Test
	public void testGetOneByIdFail() {

		WeatherDescription wd = new WeatherDescription();
		wd.setIdWeatherDescription(800);
		WeatherDescription wdesc = wdd.selectOne(wd);

		//if it doesnt find the country, the attributes are set to their default value
		assertNull(wdesc.getText());
	}

	@Test
	public void testSelectAll() {
		List <WeatherDescription> wdescs = wdd.selectAll();
		assertNotNull(wdescs);
	}

	@Test
	public void testInsertWeatherDescription() {
		
		List <WeatherDescription> wdescs1 = wdd.selectAll();

		WeatherDescription wd = new WeatherDescriptionBuilder().build();
		wdd.insert(wd);
		
		List <WeatherDescription> wdescs2 = wdd.selectAll();

		int last = wdescs2.get(wdescs2.size()-1).getIdWeatherDescription()-1;
		int beforelast = wdescs2.get(wdescs2.size()-2).getIdWeatherDescription();
		
		assertEquals(last, beforelast);
		assertThat(wdescs1.size(), not(wdescs2.size()));
		assertEquals("Rain", wdescs2.get(wdescs2.size()-1).getText());

	}
	
	@Test
	public void testUpdate() {
		WeatherDescription wd = new WeatherDescriptionBuilder().build(); // builds wdesc with id=1
		wdd.update(wd);
		WeatherDescription wdesc = wdd.selectOne(wd);

		assertEquals(1, wdesc.getIdWeatherDescription());
		assertEquals("Rain", wdesc.getText());
	}

	@Test
	public void testUpdateFail() {
		WeatherDescription wd = new WeatherDescription();
		wd.setIdWeatherDescription(800);
		wdd.update(wd);
		WeatherDescription wdesc = wdd.selectOne(wd);

		assertNull(wdesc.getText());
	}

	@Test
	public void testDelete() {
		//insert a weather description at the end
		WeatherDescription weatherdescription = new WeatherDescriptionBuilder().build();
		wdd.insert(weatherdescription);
		//make a list with all wdescriptions
		List <WeatherDescription> wdescs1 = wdd.selectAll();
		//delete the atmosphere i just created, which will be id = 5
		WeatherDescription wd = new WeatherDescription();
		wd.setIdWeatherDescription(9);
		wdd.delete(wd);
		//make a new list of wdescs
		List <WeatherDescription> wdescs2 = wdd.selectAll();
		//select the wdesc id = 9 that doesnt exist, so the values will be set to default
		wdd.selectOne(wd);

		assertThat(9, not(wdescs2.get(8).getIdWeatherDescription()));
		assertThat(wdescs1, not(wdescs2));
		assertNull(wd.getText());
	}

	@AfterClass
	public static void tearDown() {
		db.shutdown();
	}

}
