package com.globant.bootcamp.java.weatherapplication.model;

public class Town {
	private int idTown;
	private String fullName;
	private State state;
	
	
	public Town() {	}

	public Town(State state, String fullName) {
		this.fullName = fullName;
		this.state = state;
	}

	public Town(int idTown, State state, String fullName) {
		this.idTown=idTown;
		this.fullName = fullName;
		this.state = state;
	}
	
	public int getIdTown() {
		return idTown;
	}

	public void setIdTown(int idTown) {
		this.idTown = idTown;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Town [idTown=" + idTown + ", fullName=" + fullName + ", state=" + state.getIdState()
				+ "]";
	}
	
	
	
}
