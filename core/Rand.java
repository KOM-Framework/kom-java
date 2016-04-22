package core;

import java.util.Random;

public class Rand {

	public static String getCharString(int length) {
		char[] chars = (Global.ENGLISH_ALPHABET + Global.ENGLISH_ALPHABET_APPER_CASE).toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public static String getCharNumericString(int length) {
		char[] chars = (Global.ENGLISH_ALPHABET_WITH_DIGITS + Global.ENGLISH_ALPHABET_APPER_CASE).toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public static String getNumericString(int length) {
		char[] chars = Global.DIGITS.toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public static int getRandomNumber(int maxRage) {
		Random rand = new Random();
		return rand.nextInt(maxRage);
	}

	// Added By Mahbod
	public static int getRandomNumber(int minRange, int maxRange) {
		return new Random().nextInt(maxRange - minRange + 1) + minRange;
	}
}
