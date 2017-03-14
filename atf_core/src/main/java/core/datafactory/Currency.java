package core.datafactory;

import java.util.ArrayList;

public class Currency {
	private String currencyId;
	private String ISO3;
	private String currencyName;

	public Currency(String countryCode, String countryName, String ISO3166) {
		this.currencyId = countryCode;
		this.ISO3 = countryName;
		this.currencyName = ISO3166;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public String getISO3() {
		return ISO3;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public static class Currencies {
		private static ArrayList<Currency> currencies = new ArrayList<Currency>();

		public static ArrayList<Currency> list() {
			if (currencies.isEmpty()) {
				generate();
			}
			return currencies;
		}
		
		public static String getCurrencyName(String currencyId) {
			if (currencies.isEmpty()) {
				generate();
			}
			
			for (Currency c : currencies) {
				if (c.getCurrencyId().equals(currencyId))
					return c.getCurrencyName();
			}
			
			return null;
		}

		private static void generate() {
			currencies.add(new Currency("12", "DZD", "Algerian Dinar"));
			currencies.add(new Currency("31", "AZM", "Azerbaijanian Manat"));
			currencies.add(new Currency("32", "ARS", "Argentine Peso"));
			currencies.add(new Currency("36", "AUD", "Australian Dollars"));
			currencies.add(new Currency("40", "ATS", "Austrian Schilling"));
			currencies.add(new Currency("48", "BHD", "Bahraini Dinar"));
			currencies.add(new Currency("50", "BDT", "Bangladesh Taka"));
			currencies.add(new Currency("52", "BBD", "Barbados Dollar"));
			currencies.add(new Currency("56", "BEF", "Belgian Franc"));
			currencies.add(new Currency("60", "BMD", "Bermudan Dollar"));
			currencies.add(new Currency("68", "BOB", "Boliviano"));
			currencies.add(new Currency("84", "BZD", "Belize Dollar"));
			currencies.add(new Currency("124", "CAD", "Canadian Dollars"));
			currencies.add(new Currency("136", "KYD", "Cayman Island Dollar"));
			currencies.add(new Currency("144", "LKR", "Sri Lanka Rupee"));
			currencies.add(new Currency("152", "CLP", "Chliean Peso"));
			currencies.add(new Currency("156", "CNY", "Yuan Renminbi"));
			currencies.add(new Currency("170", "COP", "Colombian Peso"));
			currencies.add(new Currency("188", "CRC", "Costa Rican Colon"));
			currencies.add(new Currency("191", "HRK", "Croatian Kuna"));
			currencies.add(new Currency("196", "CYP", "Cyprus Pound"));
			currencies.add(new Currency("203", "CZK", "Czech Koruna"));
			currencies.add(new Currency("208", "DKK", "Danish Krone"));
			currencies.add(new Currency("222", "SVC", "El Salvador Colon"));
			currencies.add(new Currency("233", "EEK", "Kroon"));
			currencies.add(new Currency("242", "FJD", "Fiji Dollar"));
			currencies.add(new Currency("246", "FIM", "Finish Markka"));
			currencies.add(new Currency("250", "FRF", "French Francs"));
			currencies.add(new Currency("280", "DEM", "German Marks"));
			currencies.add(new Currency("292", "GIP", "Gibraltar Pound"));
			currencies.add(new Currency("300", "GRD", "Greek Drachma"));
			currencies.add(new Currency("320", "GTQ", "Quetzal"));
			currencies.add(new Currency("340", "HNL", "Lempira"));
			currencies.add(new Currency("344", "HKD", "Hong Kong Dollars"));
			currencies.add(new Currency("348", "HUF", "Forint"));
			currencies.add(new Currency("352", "ISK", "Iceland Krona"));
			currencies.add(new Currency("356", "INR", "Indian Rupee"));
			currencies.add(new Currency("360", "IDR", "Indonesian Rupiah"));
			currencies.add(new Currency("372", "IEP", "Irish Punt"));
			currencies.add(new Currency("376", "ILS", "New Israeli Sheqel"));
			currencies.add(new Currency("380", "ITL", "Italian Lira"));
			currencies.add(new Currency("388", "JMD", "Jamaican Dollar"));
			currencies.add(new Currency("392", "JPY", "Japanese Yen"));
			currencies.add(new Currency("398", "KZT", "Kazkhstan Tenge"));
			currencies.add(new Currency("400", "JOD", "Jordanian Dinar"));
			currencies.add(new Currency("404", "KES", "Kenyan Shilling"));
			currencies.add(new Currency("410", "KRW", "Won"));
			currencies.add(new Currency("414", "KWD", "Kuwaiti Dinar"));
			currencies.add(new Currency("422", "LBP", "Lebanese Pound"));
			currencies.add(new Currency("428", "LVL", "Lats"));
			currencies.add(new Currency("440", "LTL", "Lithuanian Litas"));
			currencies.add(new Currency("442", "LUF", "Luxembourg Franc"));
			currencies.add(new Currency("458", "MYR", "Malaysian Ringitt"));
			currencies.add(new Currency("470", "MTL", "Maltese Lira"));
			currencies.add(new Currency("484", "MXN", "Mexican Peso"));
			currencies.add(new Currency("504", "MAD", "Moroccan Dirham"));
			currencies.add(new Currency("512", "OMR", "Omani Rial"));
			currencies.add(new Currency("528", "NLG", "Dutch Guilders"));
			currencies.add(new Currency("554", "NZD", "New Zealand Dollar"));
			currencies.add(new Currency("578", "NOK", "Norwegian Krone"));
			currencies.add(new Currency("590", "PAB", "Balboa"));
			currencies.add(new Currency("604", "PEN", "Nuevo Sol"));
			currencies.add(new Currency("608", "PHP", "Philippine Peso"));
			currencies.add(new Currency("620", "PTE", "Portuguese Escudo"));
			currencies.add(new Currency("634", "QAR", "Qatari Rial"));
			currencies.add(new Currency("642", "ROL", "Romanian Lei"));
			currencies.add(new Currency("643", "RUB", "Russian Ruble"));
			currencies.add(new Currency("682", "SAR", "Saudi Riyal"));
			currencies.add(new Currency("702", "SGD", "Singapore Dollars"));
			currencies.add(new Currency("703", "SKK", "Slovak Koruna"));
			currencies.add(new Currency("705", "SIT", "Tolar"));
			currencies.add(new Currency("710", "ZAR", "South African Rand"));
			currencies.add(new Currency("724", "ESP", "Spanish Pesetas"));
			currencies.add(new Currency("752", "SEK", "Swedish Krona"));
			currencies.add(new Currency("756", "CHF", "Swiss Francs"));
			currencies.add(new Currency("764", "THB", "Baht"));
			currencies.add(new Currency("784", "AED", "UA Dirham"));
			currencies.add(new Currency("792", "TRL", "Turkish Lira"));
			currencies.add(new Currency("807", "MKD", "Macedonian Denar"));
			currencies.add(new Currency("818", "EGP", "Egyptian Pound"));
			currencies.add(new Currency("826", "GBP", "Pounds Sterling"));
			currencies.add(new Currency("840", "USD", "US Dollars"));
			currencies.add(new Currency("860", "UZS", "Uzbekistan Sum"));
			currencies.add(new Currency("862", "VEB", "Venezuela Bolivar"));
			currencies.add(new Currency("891", "CSD", "Serbian Dinars"));
			currencies.add(new Currency("901", "TWD", "New Taiwan Dollar"));
			currencies.add(new Currency("946", "RON", "Romanian New Leu"));
			currencies.add(new Currency("949", "TRY", "New Turkish Lira"));
			currencies.add(new Currency("953", "XPF", "CFP Franc"));
			currencies.add(new Currency("975", "BGN", "Bulgarian Lev"));
			currencies.add(new Currency("977", "BAM", "Convertible Marks"));
			currencies.add(new Currency("978", "EUR", "Euro"));
			currencies.add(new Currency("980", "UAH", "Hryvnia"));
			currencies.add(new Currency("985", "PLN", "Zloty"));
			currencies.add(new Currency("986", "BRL", "Brazilian Real"));
		}
	}
}