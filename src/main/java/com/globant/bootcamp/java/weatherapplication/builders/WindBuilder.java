package com.globant.bootcamp.java.weatherapplication.builders;

import com.globant.bootcamp.java.weatherapplication.model.Wind;

public class WindBuilder {
	public int idWind=1;
	public int speed=20;
	public int direction=15;
	
	public WindBuilder setSpeed(int speed) {
		this.speed=speed;
		return this;
	}
	
	public WindBuilder setIdWind(int idWind) {
		this.idWind=idWind;
		return this;
	}
	
	public WindBuilder setDirection(int direction) {
		this.direction=direction;
		return this;
	}
	
	public Wind build() {
		return new Wind(idWind,speed,direction);
	}

}
