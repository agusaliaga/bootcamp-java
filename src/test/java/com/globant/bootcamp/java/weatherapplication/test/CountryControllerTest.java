package com.globant.bootcamp.java.weatherapplication.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.globant.bootcamp.java.weatherapplication.controllers.CountryController;
import com.globant.bootcamp.java.weatherapplication.proxies.CountryProxy;

//@ContextConfiguration(locations = {"classpath:**/applicationcontext-test.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class CountryControllerTest {

//	@InjectMocks
//	CountryController cc;
	
//	@Test
//	public void getAllCountriesTest() {
//		CountryController cc = mock(CountryController.class);	
//		CountryProxy cp = mock(CountryProxy.class);
//		String r = cp.getAllCountries();
//		ResponseEntity<String> result = new ResponseEntity<String>(r, HttpStatus.OK);
//		
//				
//		try {
//			
//			when(cc.getAllCountries()).thenReturn(result);
//			assertEquals(result, cc.getAllCountries());
//			//verify(cp).getAllCountries();
//			
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//	}
//	
//	@Test
//	public void getCountryByShortCode() {
//		CountryController cc = mock(CountryController.class);
//		CountryProxy cp = mock(CountryProxy.class);
//		String r = cp.getCountryByShortName2("US");
//		ResponseEntity<String> result = new ResponseEntity<String>(r, HttpStatus.OK);
//
//		when(cc.getCountryByShortCode("US")).thenReturn(result);
//		
//		assertEquals(result, cc.getCountryByShortCode("US"));
//		verify(cp).getCountryByShortName2("US");
//
//	}
//	
//	@Test
//	public void getCountryByLongCode() {
//		CountryController cc = mock(CountryController.class);
//		CountryProxy cp = mock(CountryProxy.class);
//		String r = cp.getCountryByShortName3("USA");
//		ResponseEntity<String> result = new ResponseEntity<String>(r, HttpStatus.OK);
//
//		when(cc.getCountryByShortCode("USA")).thenReturn(result);
//		
//		assertEquals(result, cc.getCountryByShortCode("USA"));
//		verify(cp).getCountryByShortName3("USA");
//
//	}
//
//	@Test
//	public void getCountryByLongCodeFail() {
//		CountryController cc = mock(CountryController.class);
//		CountryProxy cp = mock(CountryProxy.class);
//		String r = cp.getCountryByShortName3("NZL");
//		ResponseEntity<String> result = new ResponseEntity<String>(r, HttpStatus.NOT_FOUND);
//
//		when(cc.getCountryByShortCode("NZL")).thenReturn(result);
//		
//		assertEquals(result, cc.getCountryByShortCode("NZL"));
//
//	}
//}
