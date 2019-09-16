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

import com.globant.bootcamp.java.weatherapplication.builders.StateBuilder;
import com.globant.bootcamp.java.weatherapplication.db.StateDAO;
import com.globant.bootcamp.java.weatherapplication.model.State;

@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class StateDAOTest {

	private static EmbeddedDatabase db;
	@Autowired
	StateDAO sd;
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

		State s = new StateBuilder().setIdState(1).build();
		State state = sd.selectOne(s);

		assertNotNull(state);
		assertEquals(1, state.getIdState());
		assertEquals(1, state.getCountry().getIdCountry());
		assertEquals("Andhra Pradesh", state.getFullName());
		assertEquals("AP", state.getAlpha2Code());
		assertEquals("49506799", state.getArea());
		assertEquals("Hyderabad Amaravati", state.getLargestCity());
		assertEquals("Hyderabad Amaravati", state.getCapitalCity());
	}

	@Test
	public void testGetOneByIdFail() {

		State s = new State();
		s.setIdState(800);
		State state = sd.selectOne(s);
		
		//if it doesnt find the state, the attributes are set to their default value
		assertNull(state);
	}

	@Test
	public void testSelectAll() {
		List <State> sts = sd.selectAll();
		assertNotNull(sts);
	}

	@Test
	public void testInsertState() {
		
		List <State> sts1 = sd.selectAll();

		State s = new StateBuilder().build();
		sd.insert(s);
		
		List <State> sts2 = sd.selectAll();

		int last = sts2.get(sts2.size()-1).getIdState()-1;
		int beforelast = sts2.get(sts2.size()-2).getIdState();
		
		assertNotNull(sd.selectOne(s));
		assertEquals(last, beforelast);
		assertThat(sts1.size(), not(sts2.size()));
		assertEquals(1, s.getCountry().getIdCountry());
		assertEquals("Kolkata", sts2.get(sts2.size()-1).getFullName());
		assertEquals("KO", sts2.get(sts2.size()-1).getAlpha2Code());
		assertEquals("1000000", sts2.get(sts2.size()-1).getArea());
		assertEquals("Kolkata", sts2.get(sts2.size()-1).getLargestCity());
		assertEquals("Kolkata", sts2.get(sts2.size()-1).getCapitalCity());
	}
	
	@Test
	public void testUpdate() {
		State s = new StateBuilder().build(); // builds state with id=1
		sd.update(s);
		State st = sd.selectOne(s);

		assertEquals(1, st.getIdState());
		assertEquals(1, st.getCountry().getIdCountry());
		assertEquals("Kolkata", st.getFullName());
		assertEquals("KO", st.getAlpha2Code());
		assertEquals("1000000", st.getArea());
		assertEquals("Kolkata", st.getLargestCity());
		assertEquals("Kolkata", st.getCapitalCity());
	}

	
	@Test
	public void testDelete() {
		//insert an state at the end
		State state = new StateBuilder().build();
		sd.insert(state);
		//make a list with all states
		List <State> sts1 = sd.selectAll();
		//delete the state i just created, which will be id = 11
		State s = new State();
		s.setIdState(11);
		sd.delete(s);
		//make a new list of states
		List <State> sts2 = sd.selectAll();
		//select the state id = 11 that doesnt exist, so the values will be set to default
		sd.selectOne(s);

		assertThat(11, not(sts2.get(10).getIdState()));
		assertThat(sts1, not(sts2));
		
		assertNull(s.getCountry());
		assertNull(s.getFullName());
		assertNull(s.getCapitalCity());
		assertNull(s.getAlpha2Code());
		assertEquals(null, s.getArea());
		assertNull(s.getLargestCity());
		assertNull(s.getCapitalCity());

	}

	@AfterClass
	public static void tearDown() {
		db.shutdown();
	}
	
}
