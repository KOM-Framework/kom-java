package core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class STLinkStringHelper {
	
	public static final String VERSION_STRING="StringIn=version";
	public static final String VERSIONC_STRING="StringIn=versionC";
	
	public static final String PREFIX_XML = "StringIn=<?xml version=\"1.0\"?>";
	public static final String PREFIX_KV = "StringIn=";
	public static final String START_TAG_XML = "<TMSTN>";
	public static final String START_TAG_KV = "VersionUsed^";
	
	public static final String ORDER_NUMBER_TAG="OrderNumber^";
	
	
	public static String sendSTLinkRequest(String url, String request, int connectionTimeOut, int readTimeOut) {
		String versionResp = "";

		try {
			
			versionResp = HTTP.sendPost(url, request, true, 
					connectionTimeOut, readTimeOut);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.info("Sending HTTP request failed for request.");
		}

		return versionResp;
	}
	
	
	public static String raplaceOrderNumber(String request, boolean isXML) {

		String requestLower = request.toLowerCase();
		String fieldNameBeginLower;
		String fieldNameEndLower;
		String replacedRequest = request;
		String fieldValue = "";
		String timeStamp = (new SimpleDateFormat("MMddhhmmss"))
				.format(new Date());
		
		int pos1 = -1;
		int pos2 = -1;
		int pos3 = -1;

		if (isXML) {
			fieldNameBeginLower = "<ordernumber>";
			fieldNameEndLower = "</ordernumber>";
		} else {
			fieldNameBeginLower = "~ordernumber^";
			fieldNameEndLower = "~";
		}

		pos1 = requestLower.indexOf(fieldNameBeginLower, 0);

		if (pos1 != -1) {
			pos2 = pos1 + fieldNameBeginLower.length();

			pos3 = requestLower.indexOf(fieldNameEndLower, pos2);

			if (pos3 != -1) {
				fieldValue = request.substring(pos2, pos3);

				

				replacedRequest = replacedRequest.replace(fieldValue,
						(fieldValue + "_" + timeStamp));
			}
		}
		else//ordernumber not exist add one.
		{
			replacedRequest = replacedRequest + ORDER_NUMBER_TAG+"QAAuto"+timeStamp+fieldNameEndLower;
		}
		
		return replacedRequest;
	}
	
	public static String getValue(String fieldName, String response, boolean isXML) {
		String fieldValue = "";

		String fieldNameBeginLower;
		String fieldNameEndLower;

		int pos1 = -1;
		int pos2 = -1;
		int pos3 = -1;

		if (isXML)// xml String
		{

			fieldNameBeginLower = "<" + fieldName.toLowerCase() + ">";
			fieldNameEndLower = "</" + fieldName.toLowerCase() + ">";
		} else// K-V String
		{
			fieldNameBeginLower = "~" + fieldName.toLowerCase() + "^";
			fieldNameEndLower = "~";
		}

		pos1 = response.indexOf(fieldNameBeginLower, 0);

		if (pos1 != -1) {
			pos2 = pos1 + fieldNameBeginLower.length();

			pos3 = response.indexOf(fieldNameEndLower, pos2);

			if (pos3 != -1)
				fieldValue = response.substring(pos2, pos3);

		}

		return fieldValue;
	}
	
	public static String getValueNotLowerInput(String fieldName, String response, boolean isXML) {
		String fieldValue = "";
		String responseLower = response.toLowerCase()+"~";
		
		
		String fieldNameBeginLower;
		String fieldNameEndLower;

		int pos1 = -1;
		int pos2 = -1;
		int pos3 = -1;

		if (isXML)// xml String
		{

			fieldNameBeginLower = "<" + fieldName.toLowerCase() + ">";
			fieldNameEndLower = "</" + fieldName.toLowerCase() + ">";
		} else// K-V String
		{
			fieldNameBeginLower = fieldName.toLowerCase() + "^";
			fieldNameEndLower = "~";
		}

		pos1 = responseLower.indexOf(fieldNameBeginLower, 0);

		if (pos1 != -1) {
			pos2 = pos1 + fieldNameBeginLower.length();

			pos3 = responseLower.indexOf(fieldNameEndLower, pos2);

			if (pos3 != -1)
				fieldValue = response.substring(pos2, pos3);

		}

		return fieldValue;
	}
	

	
	
	public static boolean isFieldExist(String fieldName, String response, boolean isXML) {
		boolean isFieldExist = false;

		String responseLower=response.toLowerCase();
		
		String fieldNameBeginLower;

		if (isXML)// xml String
		{
			fieldNameBeginLower = "<" + fieldName.toLowerCase() + ">";
		} else// K-V String
		{
			fieldNameBeginLower = fieldName.toLowerCase() + "^";
		}

		int pos1 = responseLower.indexOf(fieldNameBeginLower, 0);

		if (pos1 != -1) {
			isFieldExist=true;
		}

		return isFieldExist;
	}
	
}
