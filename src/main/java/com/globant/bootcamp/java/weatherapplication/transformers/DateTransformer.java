package com.globant.bootcamp.java.weatherapplication.transformers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTransformer {
	
	//"Wed, 13 Dec 2017 04:00 AM AKST",
	
	public static LocalDateTime YahooDateTimeToDateTime(String date) {
		
		String [] splitted = date.split(" ");
		
		/*for (int i=0; i<splitted.length;i++) { //to test
			System.out.println(splitted[i]);
		}*/
		String month = transformMonthToNumber(splitted[2]);
		String[] time = splitted[4].split(":");
		String meridian = splitted[5];
		
		if(meridian.equals("PM")) {
			time[0] = transformMto12(time[0], meridian);
		}
		
		String join = splitted[3] + "/" + month + "/" + splitted[1] + " " + time[0] + ":" + time[1] + ":" + "00";   
		
		//System.out.println(join);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(join, formatter);
		return dateTime;
		
	}
	
	public static String transformMonthToNumber(String month) {
		
		switch(month) {
		case "Jan" : return "01";
		case "Feb" : return "02";
		case "Mar" : return "03";
		case "Apr" : return "04";
		case "May" : return "05";
		case "Jun" : return "06";
		case "Jul" : return "07";
		case "Aug" : return "08";
		case "Sep" : return "09";
		case "Oct" : return "10";
		case "Nov" : return "11";
		case "Dec" : return "12";
		default: return null;
		}
		
	}
	public static String transformMto12 (String time, String meridian) {
		if (meridian.equalsIgnoreCase("PM")) {
			switch(time) {
			case "01": return "13";
			case "02": return "14";
			case "03": return "15";
			case "04": return "16";
			case "05": return "17";
			case "06": return "18";
			case "07": return "19";
			case "08": return "20";
			case "09": return "21";
			case "10": return "22";
			case "11": return "23";
			case "12": return "00";
			default: return null;
			}
		}
		return null;
	}

	
	public static LocalDateTime YahooDateToDateTime(String date) {
		String [] splitted = date.split(" ");
		
		/*for (int i=0; i<splitted.length;i++) { //to test
			System.out.println(splitted[i]);
		}*/
		String month = transformMonthToNumber(splitted[1]);

		String join = splitted[2] + "/" + month + "/" + splitted[0] + " " + "00"+ ":" + "00" + ":" + "00";   
		
		//System.out.println(join);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(join, formatter);
	
		return dateTime;
	}
}
