package com.globant.bootcamp.java.weatherapplication.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.globant.bootcamp.java.weatherapplication.controllers.StateController;
import com.globant.bootcamp.java.weatherapplication.controllers.WeatherController;
import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.model.Weather;
import com.globant.bootcamp.java.weatherapplication.proxies.WeatherProxy;

@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class WeatherControllerTest {
	
	
//	WeatherController wc;
//	
//	@Test
//	public void getWeatherTodayByTownStateCountry() throws IOException {
//		
//		WeatherProxy wp = mock(WeatherProxy.class);
//		Weather w = mock(Weather.class);
//		String r = wp.getWeatherToday("Sacramento", "CA", "USA").toString();
//		ResponseEntity<String> result = new ResponseEntity<String>(r, HttpStatus.OK);
//				
//		try {
//			when(wc.getWeatherTodayByTownStateCountry("Sacramento", "CA", "USA")).thenReturn(result);
//			assertEquals(result, wc.getWeatherTodayByTownStateCountry("Sacramento", "CA", "USA"));
//			verify(wp).getWeatherToday("Sacramento", "CA", "USA");			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
