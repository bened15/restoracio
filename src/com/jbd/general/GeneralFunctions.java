package com.jbd.general;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class GeneralFunctions {

	public boolean validNumber(String valueStr){
		if (valueStr.matches("[-+]?[0-9]*\\.?[0-9]+")) {
			return true;// Is a number - int, float, double         
		}
		return false;
	}

	public int asInteger(String valueStr){
		int value;
		try {
			value = Integer.parseInt(valueStr);
		} catch (NumberFormatException e) {
		    value = 0;
		}
		return  value;
	}
	
	public float asFloat(String valueStr){
		float value;
		try {
			value = Float.parseFloat(valueStr);
		} catch (NumberFormatException e) {
		    value = 0;
		}
		return  value;
	}
	
	public static Date asDate(LocalDate localDate) {
		try{
			
	    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		} catch (Exception e){
			return null;
		}
	  }
	public static LocalDate asLocalDate(Date date) {
		try{
	    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

		} catch (Exception e){
			return null;
		}
	}

	public String getId (String value){
		int endPosition =0;
		String id = null;
		endPosition = value.indexOf(" - ");
		
		if (endPosition> 0 ){
			id = value.substring(0, endPosition);
		}
		
		return id;
	}
}
