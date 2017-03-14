package core.stlink;

public class KeyValuePair {

	private StlinkField key;
	private String value;
	
	public KeyValuePair(StlinkField key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return this.key.getFieldName();
	}
	
	public String getValue() {
		return this.value;
	}
	
	public boolean matchesKey(StlinkField testKey, boolean ignoreCase) {
		if (ignoreCase) {
			return this.key.equals(testKey);
		} else {
			return this.key.equals(testKey);
		}
	}
	
	public void setValue(String newValue) {
		this.value = newValue;
	}
	
}
