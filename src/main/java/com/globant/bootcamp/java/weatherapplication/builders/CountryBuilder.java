package com.globant.bootcamp.java.weatherapplication.builders;

import com.globant.bootcamp.java.weatherapplication.model.Country;

public class CountryBuilder {
	public int idCountry = 1;
	public String fullName = "Chile";
	public String alpha2Code = "CH" ;
	public String alpha3Code = "CHL";
	
	
	public CountryBuilder setIdCountry(int idCountry) {
		this.idCountry = idCountry;
		return this;
	}
	public CountryBuilder setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}
	public CountryBuilder setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
		return this;
	}
	public CountryBuilder setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
		return this;
	}
	public Country build() {
		return new Country(idCountry,fullName,alpha2Code,alpha3Code);
	}
	
	
}
