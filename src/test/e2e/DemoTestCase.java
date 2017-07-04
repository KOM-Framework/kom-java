package e2e;

import core.utilities.WebTestListener;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.DemoDeclaration;
import pageobjects.SearchResultStructure;
import ru.yandex.qatools.allure.annotations.Parameter;

/**
 * Created by kuzov on 5/14/2016.
 */
@Listeners({WebTestListener.class})
public class DemoTestCase {

    private static DemoDeclaration searchPage = new DemoDeclaration();

    @BeforeMethod(alwaysRun = true)
    public static void beforeMethod() throws Exception {
        searchPage.invoke();
    }

    @Test
    @Parameters({"searchPattern", "expectedResult"})
    public static void testGooglePage(@Parameter("Search pattern") @Optional("Selenium") String searchPattern, @Parameter("Expected Result") @Optional("Open Source Frameworks | Sauce Labs") String expectedResult) throws Exception {
        SearchResultStructure result = null;
        try{
            searchPage.search(searchPattern);
            result = searchPage.searchForResult(expectedResult);
            Assert.assertNotNull(result,"Unable to find expected item in the Search results");
        }
        finally {
            if (result != null){
                result.mainText.click();
            }
        }
    }
}
