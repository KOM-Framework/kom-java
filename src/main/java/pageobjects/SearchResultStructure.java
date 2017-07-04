package pageobjects;

import core.datatypes.TextElement;
import core.datatypes.WebLink;
import core.web.WebDynamicInit;
import org.openqa.selenium.support.FindBy;

/**
 * Created by kuzov on 5/14/2016.
 */
public class SearchResultStructure extends WebDynamicInit {

    @FindBy(xpath=".//*[@class='r']/a") //This XPATH should be dependent on FindTable xpath
    public TextElement mainText;
    @FindBy(xpath=".//*[@class='_Rm']")
    public WebLink url;

}
