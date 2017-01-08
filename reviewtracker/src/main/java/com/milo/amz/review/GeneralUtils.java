package com.milo.amz.review;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtils {
	public static String getParameterByName (String name,String urlWithQueryString) 
	{
		Pattern p = Pattern.compile("[\\?&]" +name+ "=([^&#]*)");
		Matcher m = p.matcher(urlWithQueryString);
		while (m.find())
			return m.group().split("=")[1];
		return "";
	}
	/*public static void main(String[] args){
		System.out.println(getParameterByName("buyerID", "/gp/help/contact/contact.html?orderID=115-3168835-7461859&buyerID=A28GOCQOKN0WW6&marketplaceID=ATVPDKIKX0DER"));
	}*/
	
}
