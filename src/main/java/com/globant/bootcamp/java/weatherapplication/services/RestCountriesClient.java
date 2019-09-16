package com.globant.bootcamp.java.weatherapplication.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

public interface RestCountriesClient {

	@GET
    @Path("/get/all")
    @Produces("application/json")
    String getAllCountries();

    @GET
    @Path("/get/iso2code/{alpha2}")
    @Produces("application/json")
    String getCountryByShortName2(@PathParam("alpha2") String alpha2);

    @GET
    @Path("/get/iso3code/{alpha3}")
    @Produces("application/json")
    String getCountryByShortName3(@PathParam("alpha3") String alpha3);
	
	
}
