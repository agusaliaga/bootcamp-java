package com.globant.bootcamp.java.weatherapplication.model;

public class Country {
	private int idCountry;
	private String fullName;
	private String alpha2Code;
	private String alpha3Code;
	
	 
	public Country() {
		
	}
	
	public Country(String fullName, String alpha2Code, String alpha3Code) {
		this.fullName = fullName;
		this.alpha2Code = alpha2Code;
		this.alpha3Code = alpha3Code;
	}

	public Country(int idCountry, String fullName, String alpha2Code, String alpha3Code) {
		this.idCountry=idCountry;
		this.fullName = fullName;
		this.alpha2Code = alpha2Code;
		this.alpha3Code = alpha3Code;
	}
	
	
	public int getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
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

	public String getAlpha3Code() {
		return alpha3Code;
	}

	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}

	@Override
	public String toString() {
		return "Country [idCountry=" + idCountry + ", fullName=" + fullName + ", alpha2Code=" + alpha2Code
				+ ", alpha3Code=" + alpha3Code + "]";
	}
	
	
}
