package com.globant.bootcamp.java.weatherapplication.test;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.globant.bootcamp.java.weatherapplication.adapters.CountryAPIAdapter;
import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.services.RestCountriesClient;

@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CountryProxyTest {
	
//	@InjectMocks
//	RestCountriesClient RestCountriesClient;
//	
//	@Test
//	public void getAllCountriesTest() throws IOException {
//		List<Country> countries = new ArrayList<Country>();
//		Country c = mock(Country.class);
//		countries.add(c);
//		String ctrs = countries.toString(); 
//		RestCountriesClient.getAllCountries();
//		
//		when(RestCountriesClient.getAllCountries()).thenReturn(ctrs);
//		
//		assertEquals(countries, RestCountriesClient.getAllCountries());
//		verify(RestCountriesClient).getAllCountries();
//		
//	}
	
}
