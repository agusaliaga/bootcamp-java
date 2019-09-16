package com.globant.bootcamp.java.weatherapplication.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Wind {
	
	private int idWind;
	private int speed;
	private int direction;
	
	public Wind() {}
	
	public Wind(int id, int speed, int direction) {
		this.idWind=id;
		this.speed=speed;
		this.direction=direction;
	} 
	

	public Wind(int speed, int direction) {
		this.speed=speed;
		this.direction=direction;
	} 

	public int getIdWind() {
		return idWind;
	}

	public void setIdWind(int idWind) {
		this.idWind = idWind;
	}

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "Wind [idWind=" + idWind + ", speed=" + speed + ", direction=" + direction + "]";
	}
	
	
}
