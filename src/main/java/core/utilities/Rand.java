package core.utilities;

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
	
	public static int getRandomNumber(int maxRange) {
		Random rand = new Random();
		return rand.nextInt(maxRange);
	}
	
	public static int getRandomNumber(int minRange, int maxRange) {
		return new Random().nextInt(maxRange - minRange + 1) + minRange;
	}
	
	public static boolean isRandomField(String field) {
		return field != null ? field.equals(RAND_TAG) : false;
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
	
	public static String timestamp(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return String.valueOf(timestamp.getTime());
	}

	public static String timestamp(int length) {
		String value=timestamp();
		if (value.length()>length){
			value = value.substring((value.length()-length));
		}
		return value;
	}

    public short getRandomScore(int from, int to) {        return (short)Rand.getRandomNumber(from, to);    }

}
