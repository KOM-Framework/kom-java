package core;

import org.testng.Assert;

public class AssertLogger{
	
	public static void assertEquals(boolean actual, boolean expected){
		try{
			Assert.assertEquals(actual, expected);
			Log.info("Actual text '"+actual+"' equals to expected '"+expected+"'");
		}catch(Exception e){
			Log.error(e.getMessage());
			throw e;
		}
	}
	
	public static void assertEquals(String actual, String expected){
		try{
			Assert.assertEquals(actual, expected);
			Log.info("Actual text '"+actual+"' equals to expected '"+expected+"'");
		}catch(AssertionError e){
			Log.error(e.getMessage());
			throw e;
		}
	}
	
	public static void assertNotEquals(String actual, String expected){
		try{
			Assert.assertNotEquals(actual, expected);
			Log.info("Actual text '"+actual+"' equals to expected '"+expected+"'");
		}catch(AssertionError e){
			Log.error(e.getMessage());
			throw e;
		}
	}
	
	public static void assertTrue(boolean condition, String message){
		try{
			Assert.assertTrue(condition, message);
		}catch(AssertionError e){
			Log.error(e.getMessage());
			throw e;
		}
	}
	
	public static void assertFalse(boolean condition, String message){
		try{
			Assert.assertFalse(condition, message);
		}catch(AssertionError e){
			Log.error(e.getMessage());
			throw e;
		}
	}
	
	public static void assertNotNull(Object obj, String message){
		try{
			Assert.assertNotNull(obj, message);
		}catch(AssertionError e){
			Log.error(e.getMessage());
			throw e;
		}
	}
	
	
}
