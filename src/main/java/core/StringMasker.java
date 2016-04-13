package core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMasker {
	private static final String cc_Mask = "$2****$5";
	private static final String password_Mask = "$2********";
	
	private static final String cc_Mask_XML = "$2****$5";
	private static final String password_Mask_XML = "$2********";
	
	private static final String builtIn_MaskPattern644 = "CardNumber|AcctNumber|AcctNumber2|Track2Data|CreditCardNumber|NewAcctNumber|AcctNumberExtended|MICR";
	private static final String builtIn_MaskPattern8star = "UserPassword|AcctPin|EncryptedSerialNumber|Track1Data";

	public static String maskString(String stringToMask, boolean isXML)
	{
		String tempStringToMask = stringToMask; 
		String stringAfterMask="";
		
		Pattern pattern_644;
		Pattern Pattern_8star;
		
		if(isXML)
		{

			pattern_644 = Pattern.compile("((<(" + builtIn_MaskPattern644+ ")>\\d{6})([0-9]+)([0-9]{4}[=D]?))");
			Pattern_8star = Pattern.compile("((<("+ builtIn_MaskPattern8star + ")>)([^<]*))");
			
			
			stringAfterMask = tempStringToMask;
			
			Matcher matcher = pattern_644.matcher(stringAfterMask);
			if (matcher.find()) {
				stringAfterMask = matcher.replaceAll(cc_Mask_XML);
			}
			
			matcher = Pattern_8star.matcher(stringAfterMask);
			if (matcher.find()) {
				stringAfterMask = matcher.replaceAll(password_Mask_XML);
			}
			
		}
		else
		{
			
			tempStringToMask = tempStringToMask.endsWith("~")?tempStringToMask:tempStringToMask+"~";
			pattern_644 = Pattern.compile("(((" + builtIn_MaskPattern644+ ")[\\^];?\\d{6})([0-9]+)([0-9]{4}[=D]?))");
			Pattern_8star = Pattern.compile("((("+ builtIn_MaskPattern8star + ")([\\^]))([^~]*))");
			
			
			stringAfterMask = tempStringToMask;
			
			Matcher matcher = pattern_644.matcher(stringAfterMask);
			if (matcher.find()) {
				stringAfterMask = matcher.replaceAll(cc_Mask);
			}
			
			matcher = Pattern_8star.matcher(stringAfterMask);
			if (matcher.find()) {
				stringAfterMask = matcher.replaceAll(password_Mask);
			}
			
		}
		
		return stringAfterMask;
		
	}

/*	public static void main(String[] args) 
	{
		String a = "StringIn=TransactionType^PT~IsVerify^0~TimeOut^10000~REMOTE_ADDR^155.136.166.166~OrderNumber^PT_2016218_14:43:15_meil_0224041011~MerchantId^101011~CurrencyId^840~ExpDate^092019~AcctNumber^400068000000000001~UserName^101011ptlive~Amount^1.23~CHEnrolled^Y~VersionUsed^3~UserPassword^101011ptlive~ECI^05~IsRT^0~MOP^AD~TXStatus^Y~AcctPIN^93BC35D1710455E3~IsTest^0~AcctName^David Cue~StoreID^20140002~CAV^AAABCRFkkpFgAAAAAGSSAAAAAAA=~TRXSource^4~SecureId^12345678901234567890~RequestType^A~";
		String b = "StringIn=<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><TMSTN><VersionUsed>3</VersionUsed><MerchantId>100916</MerchantId><UserName>trx916test</UserName><UserPassword>trx916test</UserPassword><TransactionType>PT</TransactionType><IsTest>1</IsTest><TimeOut>10000</TimeOut>RequestType>A</RequestType><OrderNumber>PT_2016225_10:26:17_weinaw</OrderNumber><CurrencyId>124</CurrencyId><Amount>1.23</Amount><StoreID>1</StoreID><MOP>CC</MOP><TRXSource>4</TRXSource><AcctName>David Cue</AcctName><AcctNumber>400068000000000001</AcctNumber><ExpDate>092013</ExpDate><REMOTE_ADDR>155.136.166.166</REMOTE_ADDR><IsVerify>0</IsVerify></TMSTN>";
		System.out.println(maskString(a, false));
		
		System.out.println(maskString(b, true));
	}*/
}
