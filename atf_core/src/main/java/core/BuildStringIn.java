package core;

public class BuildStringIn {

	private static int inXMLFormat=0;
	private static String xmlEncoding="ISO-8859-1";
	private static String xmlVersion="1.0";
	
	
	private static String build(String[] params, int xmlFormat, String xmlVersion, String xmlEncoding){
		String stringIn="StringIn=";
		
		if (xmlFormat==1){
			stringIn+="<?xml version=\""+xmlVersion+"\" encoding=\""+xmlEncoding+"\"?><TMSTN>";
			for(String s:params){
				String key= s.substring(0, s.indexOf("^"));
				String value= s.substring(s.indexOf("^")+1, s.length());
				stringIn+="<"+key+">" + value + "</"+key+">";
				
			}
			stringIn+="</TMSTN>";
		}else{
			for(String s:params){
		        stringIn+=s+"~";
			    }
			 stringIn=stringIn.substring(0, stringIn.length()-1);
		}
		
		
		return stringIn;
	}
		
		public static String buildStringIn(String[] params){
			return build(params,inXMLFormat,xmlVersion,xmlEncoding);
		}
		
		public static String buildStringIn(String[] params, int inXMLFormat){
			return build(params,inXMLFormat,xmlVersion,xmlEncoding);
		}
		
		public static String buildStringIn(String[] params, int inXMLFormat, String xmlVersion){
			return build(params,inXMLFormat,xmlVersion,xmlEncoding);
		}
		
		public static String buildStringIn(String[] params, int inXMLFormat, String xmlVersion, String xmlEncoding){
			return build(params,inXMLFormat,xmlVersion,xmlEncoding);
		}
		
	
	public static void main(String[] args){
		
		String [] params = new String [] {
				"TransactionType^"+	"3D",
				"IsTest^"+ 		"0",
				"TimeOut^"+ 		"10000",
				"RequestType^"+ 	"V",
				"Amount^"+ 		"1.0",
				};
		
		System.out.println(buildStringIn(params));
		System.out.println(buildStringIn(params,1));
		System.out.println(buildStringIn(params,1,"6.0"));
		System.out.println(buildStringIn(params,1,"6.0", "ISO-888-123"));
	}
	

}
