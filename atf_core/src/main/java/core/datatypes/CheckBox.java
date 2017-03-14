package core.datatypes;

import core.web.WebItem;
import org.openqa.selenium.By;

public class CheckBox extends WebItem {

	public CheckBox(By byID) {
		super(byID);
		// TODO Auto-generated constructor stub
	}
	
	public void changeCheckStatus(boolean toCheck)
	{
		if(this.isChecked())
		{
			if(!toCheck)
				this.click();
		}
		else
		{
			if(toCheck)
				this.click();
		}			
	}

}
