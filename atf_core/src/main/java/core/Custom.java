package core;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Calendar;

public class Custom {

	public static String getMiddleValue(String originalString, String startSubStr, String endSubStr){
		String out = originalString.substring(originalString.lastIndexOf(startSubStr)+startSubStr.length(), originalString.indexOf(endSubStr));;
		return out;
	}
	
	public static ArrayList<By> byChainedToByList(Object id){
		String tempString = id.toString();
		tempString =Custom.getMiddleValue(tempString, "By.chained({", "})");
		String[] arr = tempString.split(",");
		ArrayList<By> out = new ArrayList<By>();
		for(String idString:arr){
			String expression =idString.substring(idString.indexOf(":")+1, idString.length()).trim();
			idString = idString.substring(0,idString.indexOf(":")).trim();
			switch(idString) {
				case "By.xpath":
					out.add(By.xpath(expression));
					break;
				case "By.id":
					out.add(By.id(expression));
					break;
			}
		}
		return out;
	}

	public static final String getSTNTime() {
		Calendar now = Calendar.getInstance();
		Calendar start = Calendar.getInstance();
		start.set(2001, Calendar.JANUARY, 1, 0, 0, 0);
		
		return String.valueOf((now.getTimeInMillis() - start.getTimeInMillis()) / 1000);
	}
	
}
