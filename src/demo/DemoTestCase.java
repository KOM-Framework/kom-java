package demo;

import core.AssertLogger;
import core.web.Browser;
import org.testng.annotations.Test;

/**
 * Created by kuzov on 5/14/2016.
 */
public class DemoTestCase {

    @Test
    public static void testGooglePage(){
        DemoDeclaration page = new DemoDeclaration();
        page.invoke();
        page.searchField.sendKeys("Selenium");
        page.search.click();
        Browser.sleep(500);
        SearchResultStructure result = page.searchResults.getItem("Selenium - Web Browser Automation","mainText",new SearchResultStructure());
        AssertLogger.assertNotNull(result,"Unable to find expected item in the Search results");
        result.mainText.click();
    }
}
