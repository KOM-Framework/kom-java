package pageobjects;

import core.datatypes.Button;
import core.annotations.FindTable;
import core.datatypes.Table;
import core.datatypes.TextField;
import core.web.Browser;
import core.web.WebPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by kuzov on 5/14/2016.
 */
@FindBy(xpath="//*[@title='Google']")
public class DemoDeclaration extends WebPage{

    @FindBy(id="lst-ib")
    public TextField searchField;
    @FindBy(name = "btnG")
    public Button search;
    @FindTable(xpath = "//div[@class='srg']/*[@class='g']", structure = SearchResultStructure.class, currentPageXpath = "//td[not(@class='b navend') and @class='cur']", pagerXpath = "//td[not(@class='b navend')]")
    public Table searchResults;

    @Override
    protected void invokeActions() {
        Browser.open("http://www.google.com");
    }

    @Step("Search for {0} in google")
    public void search(String searchPattern){
        this.searchField.sendKeys(searchPattern);
        this.searchField.sendKeys(Keys.ENTER);
    }

    @Step("Search for {0} in google results")
    public SearchResultStructure searchForResult(String result) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        return this.searchResults.getItem(result,"mainText");
    }
}
