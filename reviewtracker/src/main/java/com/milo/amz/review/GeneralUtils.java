package com.milo.amz.review;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;

public class GeneralUtils {
	public static String getParameterByName (String name,String urlWithQueryString) 
	{
		Pattern p = Pattern.compile("[\\?&]" +name+ "=([^&#]*)");
		Matcher m = p.matcher(urlWithQueryString);
		while (m.find())
			return m.group().split("=")[1];
		return "";
	}
	public static List<Integer> extractNumbersfromText(String text){
		List<Integer> numbersFound=new ArrayList<>();
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(text);
		while (m.find()) 
			numbersFound.add(Integer.valueOf(m.group().replace("-", "")));
		return numbersFound;
	}
	
}
