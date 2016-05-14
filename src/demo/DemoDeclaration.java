package demo;

import core.datatypes.Button;
import core.datatypes.GridView;
import core.datatypes.TextField;
import core.web.Browser;
import core.web.WebItem;
import core.web.WebPage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

/**
 * Created by kuzov on 5/14/2016.
 */
public class DemoDeclaration extends WebPage{

    @FindBy(xpath="//*[@title='Google']")
    public WebItem webPageId;               //MUST HAVE EVERY PAGE

    @FindBy(name="q")
    public TextField searchField;
    @FindBy(name = "btnG")
    public Button search;
    @FindBys({@FindBy(xpath = "//*[@class='g']"),
              @FindBy(id = "pnnext")})
    public GridView searchResults;

    @Override
    protected void invokeActions() {
        Browser.getDriver().get("https://www.google.ca");
    }
}
