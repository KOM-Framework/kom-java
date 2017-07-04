package core.datatypes;

import core.web.KomElement;
import org.openqa.selenium.By;

public class CheckBox extends KomElement {

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
