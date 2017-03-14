package core.stlink;

public enum StringFormat {
	
	XML("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><TMSTN>", "</TMSTN>"),
	KVP("", "");
	
	private String header;
	private String footer;
	
	private StringFormat(String header, String footer) {
		this.header = header;
		this.footer = footer;
	}
	
	public String getHeader() {
		return header;
	}
	
	public String getFooter() {
		return footer;
	}
	
}
