package com.globant.bootcamp.java.weatherapplication.transformers;

public class TemperatureTransformer {
	public static int fToC(int f) {
		
		int c = (f-32)*5/9;
		return c;
	}
}
