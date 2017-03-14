package core.stlink;

import core.Global;
import core.StringMasker;
import core.StringMasker.MaskPattern;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StlinkString {

	private StringFormat format;
	private ArrayList<KeyValuePair> kvpList;
	
	public StlinkString(StringFormat format) {
		this.format = format;
		this.kvpList = new ArrayList<KeyValuePair>();
	}
	
	public StlinkString(String stlinkString, StringFormat format) {
		this(format);
		parseString(stlinkString);
	}
	
	public StringFormat getFormat() {
		return this.format;
	}
	
	public ArrayList<KeyValuePair> getKvpList() {
		return this.kvpList;
	}

	public String getValue(StlinkField key) {
		for (KeyValuePair kvp : kvpList) {
			if (kvp.matchesKey(key, true)) {
				return kvp.getValue();
			}
		}
		return null;
	}
	
	public void add(StlinkField key, String value) {
		if (key != null && value != null) {
			this.kvpList.add(new KeyValuePair(key, value));
		}
	}
	
	public void add(KeyValuePair kvp) {
		this.kvpList.add(kvp);
	}
	
	public void replace(StlinkField key, String newValue) {
		for (KeyValuePair kvp : kvpList) {
			if (kvp.matchesKey(key, true) && newValue != null) {
				kvp.setValue(newValue);
			}
		}
	}
	
	private void parseString(String string) {
		Pattern pattern;
		Matcher matcher;
		
		switch (format) {
		case KVP:
			String[] kvps = string.split("~");
			
			for (String kvp : kvps) {
				if (!kvp.isEmpty()) {
					String[] s = kvp.split("\\^", -1);
					kvpList.add(new KeyValuePair(StlinkField.findEnumValue(s[0]), s[1]));
				}
			}
			break;
			
		case XML:
			pattern = Pattern.compile("<(" + Global.CHARSET + "{1,})>(" + Global.CHARSET + "{1,})</" + Global.CHARSET + "{1,}>");
			matcher = pattern.matcher(string);
			
			while (matcher.find()) {
				kvpList.add(new KeyValuePair(StlinkField.findEnumValue(matcher.group(1)), matcher.group(2)));
			}
			break;
			
		default:
			break;
		}
	}
	
	public String build() {
		String stlinkString = "";
		
		switch (format) {
		case XML:
			for (KeyValuePair kvp : kvpList) {
				stlinkString += "<" + kvp.getKey() + ">" + kvp.getValue() + "</" + kvp.getKey() + ">\n";
			}
			break;
			
		case KVP:
			ArrayList<String> kvps = new ArrayList<String>();
			for (KeyValuePair kvp : kvpList) {
				kvps.add(kvp.getKey() + "^" + kvp.getValue());
			}
			stlinkString = String.join("\n~", kvps);
			break;
		}
		
		return stlinkString;
	}
	
	public String buildMasked() {
		String maskedString = "";
		
		switch (format) {
		case XML:
			for (KeyValuePair kvp : kvpList) {
				String key = kvp.getKey();
				String value = kvp.getValue();
				
				if (key.equalsIgnoreCase("userpassword")) {
					value = StringMasker.maskString(value, MaskPattern.PATTERN_PASSWORD);
				} else if (key.toLowerCase().contains("acctnumber")) {
					value = StringMasker.maskString(value, MaskPattern.PATTERN_644);
				}
				
				maskedString += "<" + key + ">" + value + "</" + key + ">\n";
			}
			break;
			
		case KVP:
			ArrayList<String> kvps = new ArrayList<String>();
			for (KeyValuePair kvp : kvpList) {
				String key = kvp.getKey();
				String value = kvp.getValue();
				
				if (key.toLowerCase().equals("userpassword")) {
					value = StringMasker.maskString(value, MaskPattern.PATTERN_PASSWORD);
				} else if (key.toLowerCase().contains("acctnumber")) {
					value = StringMasker.maskString(value, MaskPattern.PATTERN_644);
				}
				
				kvps.add(key + "^" + value);
			}
			maskedString = String.join("\n~", kvps);
			break;
		}
		
		return maskedString;
	}

}
