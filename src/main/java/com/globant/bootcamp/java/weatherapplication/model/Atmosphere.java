package com.globant.bootcamp.java.weatherapplication.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Atmosphere {
	
	private int idAtmosphere;
	private int humidity;
	private double pressure;
	private int rising;
	private double visibility;
	
	public Atmosphere() {
		
		
	}
	
	public Atmosphere(int humidity, double pressure, int rising, double visibility) {
		this.humidity = humidity;
		this.pressure = pressure;
		this.rising = rising;
		this.visibility = visibility;
	}
	
	
	/*Constructor 2 asks for all the attributes*/
	public Atmosphere(int id, int humidity, double pressure, int rising, double visibility) {
		this.idAtmosphere=id;
		this.humidity = humidity;
		this.pressure = pressure;
		this.rising = rising;
		this.visibility = visibility;
	}

	public int getIdAtmosphere() {
		return idAtmosphere;
	}
	public void setIdAtmosphere(int idAtmosphere) {
		this.idAtmosphere = idAtmosphere;
	}
	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(long pressure) {
		this.pressure = pressure;
	}

	public int getRising() {
		return rising;
	}

	public void setRising(int rising) {
		this.rising = rising;
	}

	public double getVisibility() {
		return visibility;
	}

	public void setVisibility(long visibility) {
		this.visibility = visibility;
	}

	@Override
	public String toString() {
		return "Atmosphere [idAtmosphere=" + idAtmosphere + ", humidity=" + humidity + ", pressure=" + pressure
				+ ", rising=" + rising + ", visibility=" + visibility + "]";
	}

}
