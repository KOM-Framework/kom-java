package core;

import core.datafactory.Country.Countries;
import fabricator.Fabricator;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

public class Rand {
	
	public static final String RAND_TAG = "!RAND";
	
	private static String getString(char[] chars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static String getCharString(int length) {
		return getString((Global.ENGLISH_ALPHABET + Global.ENGLISH_ALPHABET_UPPER_CASE).toCharArray(), length);
	}
	
	public static String getCharNumericString(int length) {
		return getString((Global.ENGLISH_ALPHABET_WITH_DIGITS + Global.ENGLISH_ALPHABET_UPPER_CASE).toCharArray(), length);
	}
	
	public static String getNumericString(int length) {
		return getString(Global.DIGITS.toCharArray(), length);
	}
	
	public static int getRandomNumber(int maxRange) {
		Random rand = new Random();
		return rand.nextInt(maxRange);
	}
	
	//Added By Mahbod
	public static int getRandomNumber(int minRange, int maxRange) {
		return new Random().nextInt(maxRange - minRange + 1) + minRange;
	}
	
	public enum Field {
		Amount,
		CreditCard,
		CVN,
		IssueNumber,
		StartDate,
		ExpiryDate,
		FullName,
		FirstName,
		LastName,
		PhoneNumber,
		PhoneExtension,
		Address1,
		Address2,
		City,
		StateCode,
		CountryCode,
		Country,
		ZipCode,
		Email,
		Title,
		Company,
		MiddleName,
		Suffix
	}
	
	public static boolean isRandomField(String field) {
		return field != null ? field.equals(RAND_TAG) : false;
	}
	
	public static String check(String fieldToCheck, Field fieldType) {
		if (isRandomField(fieldToCheck)) {
			switch (fieldType) {
			case Address1: return address1();
			case Address2: return address2();
			case Amount: return amount();
			case City: return city();
			case Company: return company();
			case Country: return country();
			case CountryCode: return countryCode();
			case CreditCard: return creditCard();
			case CVN: return cvn();
			case Email: return email();
			case ExpiryDate: return expiryDate();
			case FirstName: return firstName();
			case FullName: return fullName();
			case IssueNumber: return issueNumber();
			case LastName: return lastName();
			case MiddleName: return middleName();
			case PhoneExtension: return phoneExtension();
			case PhoneNumber: return phoneNumber();
			case Suffix: return suffix();
			case StartDate: return startDate();
			case StateCode: return stateCode();
			case Title: return title();
			case ZipCode: return zipCode();
			default: break;
			}
		}
		
		return fieldToCheck;
	}
	
	public static String getOption(ArrayList<String> options) {
		return options.get(new Random().nextInt(options.size()));
	}
	
	public static String amount() {
		return String.format("%.2f", new Random().nextFloat() * 100);
	}
	
	public static String creditCard() {
		String generatedCard = getRandomNumber(0, 1) == 0 ? Fabricator.finance().masterCard() : Fabricator.finance().visaCard();
		int length = generatedCard.length();
		String cardWithoutCheckDigit = generatedCard.substring(0, length - 1);
		
		char[] reversedCardCharArray = new StringBuilder(cardWithoutCheckDigit).reverse().toString().toCharArray();
		int[] reversedCard = new int[length - 1];
		for (int i = 0; i < length - 1; i++) {
			reversedCard[i] = Character.getNumericValue(reversedCardCharArray[i]);
		}
		
		int sum = 0;
		int pos = 0;
		
		while (pos < length - 1) {
			int odd = reversedCard[pos] * 2;
			if (odd > 9)
				odd -= 9;
			sum += odd;
			if (pos != (length - 2))
				sum += reversedCard[pos + 1];
			pos += 2;
		}
		
		int checkDigit = ((sum / 10 + 1) * 10 - sum) % 10;
		
		return cardWithoutCheckDigit + String.valueOf(checkDigit);
	}
	
	public static String getDateBetween(DateTime startDate, DateTime endDate, String format) {
		return Fabricator.calendar().datesRange()
				.startDate(startDate)
				.endDate(endDate)
				.getRandomDate()
				.toString(format);
	}
	
	public static String expiryDate() {
		DateTime now = DateTime.now();
		DateTime future = DateTime.now().plusYears(5);
		return getDateBetween(now, future, "MM/yyyy");
	}
	
	public static String startDate() {
		DateTime now = DateTime.now();
		DateTime past = DateTime.now().minusYears(5);
		return getDateBetween(past, now, "MM/yyyy");
	}
	
	public static String issueNumber() {
		return getNumericString(2);
	}

	public static String fullName() {
		return Fabricator.contact().fullName(false, false);
	}

	public static String firstName() {
		return Fabricator.contact().firstName();
	}
	
	public static String middleName() {
		return Fabricator.contact().firstName();
	}

	public static String lastName() {
		return Fabricator.contact().lastName();
	}

	public static String address1() {
		return Fabricator.contact().houseNumber() + " " + Fabricator.contact().streetName();
	}
	
	public static String address2() {
		return Fabricator.contact().apartmentNumber();
	}

	public static String city() {
		return Fabricator.contact().city();
	}

	public static String stateCode() {
		return Fabricator.contact().stateShortCode();
	}

	public static String country() {
		return Countries.list().get(new Random().nextInt(Countries.list().size())).getCountryName();
	}
	
	public static String countryCode() {
		return Countries.list().get(new Random().nextInt(Countries.list().size())).getCountryCode();
	}

	public static String zipCode() {
		return Fabricator.contact().postcode();
	}

	public static String email() {
		return Fabricator.contact().eMail();
	}

	public static String title() {
		return Fabricator.contact().prefix();
	}

	public static String company() {
		return Fabricator.contact().company();
	}

	public static String suffix() {
		return Fabricator.contact().suffix();
	}
	
	public static String phoneNumber() {
		return getNumericString(10);
	}
	
	public static String phoneExtension() {
		return getNumericString(4);
	}
	
	public static String cvn() {
		return getNumericString(3);
	}
	
	public static String timestamp(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return String.valueOf(timestamp.getTime());
	}
	
}
