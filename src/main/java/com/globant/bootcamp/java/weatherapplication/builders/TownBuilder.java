package com.globant.bootcamp.java.weatherapplication.builders;

import com.globant.bootcamp.java.weatherapplication.model.State;
import com.globant.bootcamp.java.weatherapplication.model.Town;

public class TownBuilder {
	public int idTown=1;
	public State state=new StateBuilder().build();
	public String fullName="Kestopur";
	
	
	
	public TownBuilder setIdTown(int idTown) {
		this.idTown = idTown;
		return this;
	}
	public TownBuilder setState(State state) {
		this.state = state;
		return this;
	}
	public TownBuilder setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}
	public Town build() {
		return new Town(idTown,state,fullName);
	}
	
}
