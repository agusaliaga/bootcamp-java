package com.globant.bootcamp.java.weatherapplication.builders;

import com.globant.bootcamp.java.weatherapplication.model.Atmosphere;

public class AtmosphereBuilder {
	public int idAtmosphere=3;
	public int humidity=50;
	public double pressure=1120;
	public int rising=2;
	public double visibility=15;
	
	public AtmosphereBuilder setIdAtmosphere(int idAtmosphere) {
		this.idAtmosphere = idAtmosphere;
		return this;
	}
	public AtmosphereBuilder setHumidity(int humidity) {
		this.humidity = humidity;
		return this;
	}
	public AtmosphereBuilder setPressure(double d) {
		this.pressure = d;
		return this;
	}
	public AtmosphereBuilder setRising(int rising) {
		this.rising = rising;
		return this;
	}
	public AtmosphereBuilder setVisibility(double d) {
		this.visibility = d;
		return this;
	}
	
	public Atmosphere build() {
		return new Atmosphere(idAtmosphere, humidity, pressure, rising, visibility);
	}
	
	
}
