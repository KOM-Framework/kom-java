package core;

import java.util.ArrayList;
import org.openqa.selenium.By;

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
	
}
