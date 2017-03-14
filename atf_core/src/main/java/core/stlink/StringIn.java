package core.stlink;

import core.Global;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StringIn extends StlinkString {
	
	public StringIn(StringFormat format) {
		super(format);
	}
	
	public StringIn(String stringIn, StringFormat format) {
		super(stringIn, format);
	}
	
	public StringIn(String version, String merchantId, String username, String password, String transType, String isTest, String timeout, StringFormat format) {
		super(format);
		add(StlinkField.VERSION_USED, version);
		add(StlinkField.MERCHANT_ID, merchantId);
		add(StlinkField.USER_NAME, username);
		add(StlinkField.USER_PASSWORD, password);
		add(StlinkField.TRANSACTION_TYPE, transType);
		add(StlinkField.IS_TEST, isTest);
		add(StlinkField.TIME_OUT, timeout);
	}
	
	public StringIn(Merchant merchant, StringFormat format) {
		super(format);
		add(StlinkField.VERSION_USED, String.valueOf(Global.STLINK_VERSION));
		add(StlinkField.MERCHANT_ID, merchant.getMerchantId());
		add(StlinkField.USER_NAME, merchant.getUsername());
		add(StlinkField.USER_PASSWORD, merchant.getPassword());
		add(StlinkField.TRANSACTION_TYPE, merchant.getService().getServiceCode());
		add(StlinkField.IS_TEST, merchant.getService().getIsTest());
		add(StlinkField.TIME_OUT, String.valueOf(Global.STLINK_TIMEOUT));
	}
	
	public String build() {
		return "StringIn=" + getFormat().getHeader() + super.build() + getFormat().getFooter();
	}
	
	public void addOrderNumber() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		dateFormatter.setTimeZone(c.getTimeZone());
		String orderNumber = System.getProperty("user.name") + "_" + dateFormatter.format(c.getTime());
		
		add(StlinkField.ORDER_NUMBER, orderNumber);
	}
	
}
