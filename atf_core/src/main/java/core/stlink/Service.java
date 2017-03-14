package core.stlink;

public enum Service {

	PT_LIVE(2, "PT", false),
	PT_TEST(9, "PT", true),
	RD_LIVE(22, "RD", false),
	RD_TEST(23, "RD", true),
	RT_LIVE(24, "RT", false),
	RT_TEST(25, "RT", true),
	BR_LIVE(32, "BR", false),
	BR_TEST(33, "BR", true);
	
	private int serviceId;
	private String transType;
	private boolean isTest;
	
	private Service(int serviceId, String transType, boolean isTest) {
		this.serviceId = serviceId;
		this.transType = transType;
		this.isTest = isTest;
	}
	
	public int getServiceId() {
		return this.serviceId;
	}
	
	public String getServiceCode() {
		return this.transType;
	}
	
	public boolean isTest() {
		return this.isTest;
	}
	
	public String getIsTest() {
		return (this.isTest ? "1" : "0");
	}
	
}