package com.globant.bootcamp.java.weatherapplication.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


@Path("/v1/")
public interface RestYahooClient {
	
	@GET
	@Path("public/yql")
	@Produces("application/json")
	String getWeather(@QueryParam("q") String q, @QueryParam("format") String format);
}
