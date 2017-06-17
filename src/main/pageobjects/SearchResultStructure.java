package pageobjects;

import core.web.WebDynamicInit;
import core.web.WebItem;
import org.openqa.selenium.support.FindBy;

/**
 * Created by kuzov on 5/14/2016.
 */
public class SearchResultStructure extends WebDynamicInit {

    @FindBy(xpath="//*[@class='r']/a") //This XPATH should be dependent on GridView xpath
    public WebItem mainText;
    @FindBy(xpath="//*[@class='_Rm']")
    public WebItem url;

}
