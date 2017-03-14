package core.stlink;

public enum StlinkField {

	VERSION_USED("VersionUsed"),
	MERCHANT_ID("MerchantId"),
	USER_NAME("UserName"),
	USER_PASSWORD("UserPassword"),
	TRANSACTION_TYPE("TransactionType"),
	IS_TEST("IsTest"),
	TIME_OUT("TimeOut"),
	REQUEST_TYPE("RequestType"),
	
	STORE_ID("StoreID"),
	ORDER_NUMBER("OrderNumber"),
	SVC("SVC"),
	SVCRT("SVCRT"),
	SVCID("SVCID"),
	CURRENCY_ID("CurrencyId"),
	AMOUNT("Amount"),
	ACCT_NUMBER2("AcctNumber2"),
	AUTH_CODE("AuthCode"),
	CUSTOMER_ID("CustomerId"),
	RESULT_URL("ResultURL"),
	CANCEL_URL("CancelURL"),
	CSS_URL("CSSURL"),
	MERCHANT_DATA("MerchantData"),
	DISABLE_UPDATE("DisableUpdate"),
	BILLING_OVERRIDE("BillingOverride"),
	SHIPPING_OVERRIDE("ShippingOverride"),
	
	TITLE("Title"),
	COMPANY("Company"),
	FIRST_NAME("FirstName"),
	MIDDLE_NAME("MiddleName"),
	LAST_NAME("LastName"),
	SUFFIX("Suffix"),
	ADDRESS1("Address1"),
	ADDRESS2("Address2"),
	ADDRESS3("Address3"),
	CITY("City"),
	STATE_CODE("StateCode"),
	ZIP_CODE("ZipCode"),
	COUNTRY_CODE("CountryCode"),
	PHONE_NUMBER("PhoneNumber"),
	PHONE_EXTENSION("PhoneExtension"),
	EMAIL("Email"),
	
	SHIP_TO_TITLE("ShipToTitle"),
	SHIP_TO_COMPANY("ShipToCompany"),
	SHIP_TO_FIRST_NAME("ShipToFirstName"),
	SHIP_TO_MIDDLE_NAME("ShipToMiddleName"),
	SHIP_TO_LAST_NAME("ShipToLastName"),
	SHIP_TO_SUFFIX("ShipToSuffix"),
	SHIP_TO_ADDRESS1("ShipToAddress1"),
	SHIP_TO_ADDRESS2("ShipToAddress2"),
	SHIP_TO_ADDRESS3("ShipToAddress3"),
	SHIP_TO_CITY("ShipToCity"),
	SHIP_TO_STATE_CODE("ShipToStateCode"),
	SHIP_TO_ZIP_CODE("ShipToZipCode"),
	SHIP_TO_COUNTRY_CODE("ShipToCountryCode"),
	SHIP_TO_PHONE_NUMBER("ShipToPhoneNumber"),
	SHIP_TO_PHONE_EXTENSION("ShipToPhoneExtension"),
	
	RT_START_DATE("RTStartDate"),
	RT_END_DATE("RTEndDate"),
	RT_FREQUENCY_VALUE("RTFrequencyValue"),
	RT_FREQUENCY_TYPE("RTFrequencyType"),
	RT_STATUS("RTStatus"),
	MERCHANT_REFERENCE("MerchantReference"),
	CARD_ID("CardId"),
	BILLING_SOURCE("BillingSource"),
	SHIPPING_SOURCE("ShippingSource"),
	
	STR_ID("StrId"),
	RD_ID("RDID"),
	MESSAGE_CODE("MessageCode"),
	MESSAGE("Message"),
	REDIRECT_URL("RedirectURL"),
	UTC("UTC"),
	
	TOKEN("Token"),
	ACCT_NUMBER("AcctNumber"),
	ACCT_NAME("AcctName"),
	ACCT_NUMBER_SHA1("AcctNumberSHA1"),
	CREDIT_CARD_TYPE("CreditCardType"),
	ISSUE_COUNTRY("IssueCountry"),
	EXP_DATE("ExpDate");
	
	private String fieldName;
	private StlinkField(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return fieldName;
	}
	
	public static StlinkField findEnumValue(String stringValue) {
		for (StlinkField field : StlinkField.values()) {
			if (field.getFieldName().equalsIgnoreCase(stringValue)) {
				return field;
			}
		}
		
		return null;
	}
	
}
