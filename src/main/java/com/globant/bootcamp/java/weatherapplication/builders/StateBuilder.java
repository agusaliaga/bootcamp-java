package com.globant.bootcamp.java.weatherapplication.builders;

import com.globant.bootcamp.java.weatherapplication.model.Country;
import com.globant.bootcamp.java.weatherapplication.model.State;

public class StateBuilder {
	public int idState = 1;
	public Country country = new CountryBuilder().build();
	public String fullName = "Kolkata";
	public String alpha2Code = "KO";
	public String area = "1000000";
	public String largestCity = "Kolkata";
	public String capitalCity = "Kolkata";
	
	public StateBuilder setIdState(int idState) {
		this.idState = idState;
		return this;
	}
	public StateBuilder setCountry(Country country) {
		this.country = country;
		return this;
	}
	
	public StateBuilder setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}
	public StateBuilder setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
		return this;
	}
	public StateBuilder setArea(String area) {
		this.area = area;
		return this;
	}
	public StateBuilder setLargestCity(String largestCity) {
		this.largestCity = largestCity;
		return this;
	}
	public StateBuilder setCapitalCity(String capitalCity) {
		this.capitalCity = capitalCity;
		return this;
	}


	public State build() {
		return new State(idState,country,fullName,alpha2Code,area,largestCity,capitalCity);
	}
	
	
}
