package com.globant.bootcamp.java.weatherapplication.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.globant.bootcamp.java.weatherapplication.controllers.StateController;
import com.globant.bootcamp.java.weatherapplication.model.State;

@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class StateControllerTest {
	
	
	@Test
	public void getAllStatesOfOneCountryTest() {
		StateController sc = mock(StateController.class);
		List<State> states = new ArrayList<State>();
		State s = mock(State.class);
		states.add(s);
		String r = states.toString();
		ResponseEntity<String> result = new ResponseEntity<String>(r, HttpStatus.OK);
				
		try {
			when(sc.getAllStates("US")).thenReturn(result);
			
			assertEquals(result, sc.getAllStates("US"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getOneState() {
		StateController sc = mock(StateController.class);
		State s = mock(State.class);
		String r = s.toString();
		ResponseEntity<String> result = new ResponseEntity<String>(r, HttpStatus.OK);
				
		try {
			when(sc.getOneState("USA", "AK")).thenReturn(result);
			
			assertEquals(result, sc.getOneState("USA", "AK"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
