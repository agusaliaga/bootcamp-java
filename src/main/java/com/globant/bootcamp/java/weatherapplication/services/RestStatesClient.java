package com.globant.bootcamp.java.weatherapplication.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

public interface RestStatesClient {
	//Get all states from a country (only USA and IND available)
    @GET
    @Path("/get/{alpha3}/all")
    @Produces("application/json")
    String getStateByLongCountryCode(@PathParam("alpha3") String alpha3);

    @GET
    @Path("/get/{countryAlpha3}/{stateAlpha2}")
    @Produces("application/json")
    String getStateByLongCountryCodeShortStateCode(@PathParam("countryAlpha3") String countryAlpha3, 
    		@PathParam("stateAlpha2") String stateAlpha2);
}
