package core.datatypes;

import core.Log;
import core.web.WebItem;
import org.openqa.selenium.By;

public class SliderBetween extends WebItem {

	WebItem sliderLower;
	WebItem sliderUpper;

	public SliderBetween(By byID, By byIDLower, By byIDUpper) {
		super(byID);
		sliderLower = new WebItem(byIDLower);
		sliderUpper = new WebItem(byIDUpper);
	}
	
	public int getLowerValue() {
		return Integer.parseInt(sliderLower.getText());
	}
	
	public int getUpperValue() {
		return Integer.parseInt(sliderUpper.getText());
	}
	
	public void changeSlidersByOffset(int lowerOffset, int upperOffset) {
		changeLowerSliderByOffset(lowerOffset);
		changeUpperSliderByOffset(upperOffset);
		Log.info("Slider changed by offset [lower = " + lowerOffset + ", upper = " + upperOffset + "].");
	}
	
	public void changeSlidersTo(int lowerValue, int upperValue) {
		changeLowerSliderTo(lowerValue);
		changeUpperSliderTo(upperValue);
	}
	
	public void changeSlidersTo(String lowerValue, String upperValue) {
		try {
			changeSlidersTo(Integer.parseInt(lowerValue), Integer.parseInt(upperValue));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public void changeLowerSliderByOffset(int offset) {
		sliderLower.dragAndDropBy(Math.round(getOffsetRatio() * offset), 0);
	}
	
	public void changeUpperSliderByOffset(int offset) {		
		sliderUpper.dragAndDropBy(Math.round(getOffsetRatio() * offset), 0);
	}
	
	public void changeLowerSliderTo(int value) {
		changeLowerSliderByOffset(value - getLowerValue());
	}
	
	public void changeUpperSliderTo(int value) {
		changeUpperSliderByOffset(value - getUpperValue());
	}
	
	private float getOffsetRatio() {
		return (float) getSize().width / 100;
	}

}
