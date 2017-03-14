package core.stlink;

public class Merchant {
	
	private String merchantId;
	private String username;
	private String password;
	private Service service;
	
	public Merchant(String merchantId, String username, String password, Service service) {
		this.merchantId = merchantId;
		this.username = username;
		this.password = password;
		this.service = service;
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Service getService() {
		return service;
	}
	
}
