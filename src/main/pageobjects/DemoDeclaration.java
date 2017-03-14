package pageobjects;

import core.datatypes.Button;
import core.datatypes.Grid;
import core.datatypes.GridView;
import core.datatypes.TextField;
import core.web.Browser;
import core.web.WebItem;
import core.web.WebPage;
import org.openqa.selenium.support.FindBy;
import resources.DemoGlobal;
import ru.yandex.qatools.allure.annotations.Step;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by kuzov on 5/14/2016.
 */
public class DemoDeclaration extends WebPage{

    @FindBy(xpath="//*[@value=\"I'm Feeling Lucky\"]")
    public WebItem webPageId;               //MUST HAVE EVERY PAGE

    @FindBy(id="lst-ib")
    public TextField searchField;
    @FindBy(name = "btnG")
    public Button search;
    @Grid(xpath = "//div[@class='srg']/*[@class='g']", structure = SearchResultStructure.class, currentPageXpath = "//td[not(@class='b navend') and @class='cur']", pagerXpath = "//td[not(@class='b navend')]")
    public GridView searchResults;

    @Override
    protected void invokeActions() {
        Browser.open(DemoGlobal.applicationUrl);
    }

    @Step("Search for {0} in google")
    public void search(String searchPattern){
        this.searchField.sendKeys(searchPattern);
        this.search.click();
    }

    @Step("Search for {0} in google results")
    public SearchResultStructure searchForResult(String result) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        return this.searchResults.getItem(result,"mainText", 5);
    }
}
