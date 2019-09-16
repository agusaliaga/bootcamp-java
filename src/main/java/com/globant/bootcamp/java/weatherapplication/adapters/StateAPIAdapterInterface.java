package com.globant.bootcamp.java.weatherapplication.adapters;

import java.util.List;

import com.globant.bootcamp.java.weatherapplication.model.State;

public interface StateAPIAdapterInterface {
	public List<State> getStateByLongCountryCode(String alpha3);
	public State getStateByLongCountryCodeShortStateCode(String alpha3country, String alpha2state);
}
