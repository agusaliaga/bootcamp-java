package com.globant.bootcamp.java.weatherapplication.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class State {
	private int idState;
	private String fullName;
	private String alpha2Code;
	private String area;
	private String largestCity;
	private String capitalCity;
	private Country country;

	public State() {
		
	}
	
	public State(String fullName,  Country country, String alpha2Code, 
			String area, String largestCity, String capitalCity) {
		this.country = country;
		this.fullName = fullName;
		this.alpha2Code = alpha2Code;
		this.area = area;
		this.largestCity = largestCity;
		this.capitalCity = capitalCity;
		
	}
	
	public State(int idState,Country country, String fullName, String alpha2Code, 
			String area, String largestCity, String capitalCity) {
		this.idState=idState;
		this.country = country;
		this.fullName = fullName;
		this.alpha2Code = alpha2Code;
		this.area = area;
		this.largestCity = largestCity;
		this.capitalCity = capitalCity;
	
	}

	public int getIdState() {
		return idState;
	}

	public void setIdState(int idState) {
		this.idState = idState;
	}

	public Country getCountry() {
		return country;
	}

	public Country setCountry(Country c) {
		return this.country = c;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getAlpha2Code() {
		return alpha2Code;
	}


	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getLargestCity() {
		return largestCity;
	}


	public void setLargestCity(String largestCity) {
		this.largestCity = largestCity;
	}


	public String getCapitalCity() {
		return capitalCity;
	}


	public void setCapitalCity(String capitalCity) {
		this.capitalCity = capitalCity;
	}

	@Override
	public String toString() {
		return "State [idState=" + idState + ", fullName=" + fullName + ", alpha2Code=" + alpha2Code + ", area=" + area
				+ ", largestCity=" + largestCity + ", capitalCity=" + capitalCity + ", country=" + country.getIdCountry()  
				+ ", name=" + country.getFullName() + ", alpha2_code=" + country.getAlpha2Code() + ", alpha3_code=" + country.getAlpha3Code() + "]";
	}

	
}
