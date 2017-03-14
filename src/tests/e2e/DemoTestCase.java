import core.WebTestListener;
import core.groups.Story;
import core.groups.TestGroup;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.DemoDeclaration;
import pageobjects.SearchResultStructure;
import ru.yandex.qatools.allure.annotations.*;

/**
 * Created by kuzov on 5/14/2016.
 */
@Features("Demo Test Feature")
@Listeners({WebTestListener.class})
public class DemoTestCase {

    private static DemoDeclaration page;

    @BeforeMethod(alwaysRun = true)
    public static void beforeMethod() throws Exception {
        page = new DemoDeclaration();
        page.invoke();
    }

    @Test(groups = TestGroup.acceptance)
    @Title("This is a demo test title")
    @Description("This is a demo test description")
    @Stories(Story.functional)
    @Parameters({"searchPattern", "expectedResult"})
    public static void testGooglePage(@Parameter("Search pattern") @Optional("Selenium") String searchPattern, @Parameter("Expected Result") @Optional("Selenium Users - Google Groups") String expectedResult) throws Exception {
        SearchResultStructure result = null;
        try{
            page.search(searchPattern);
            result = page.searchForResult(expectedResult);
            Assert.assertNotNull(result,"Unable to find expected item in the Search results");
        }
        finally {
            if (result != null){
                result.mainText.click();
            }
        }
    }
}
