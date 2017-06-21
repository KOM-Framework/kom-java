package core.web;

import core.Log;
import core.Reflect;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by olehk on 31/03/2017.
 */
public abstract class BasePage extends WebDynamicInit {

    protected String pageName = this.getClass().getName();

    protected BasePage() {
        initElements(this);
    }

    public boolean exists(int... waitTime) throws Exception {
        int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
        boolean result = false;
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitValue);
        try {
            WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated((By) Reflect.getFieldValueFromField(this, "webPageId", "byId")));
            result = item != null;
        } catch (TimeoutException ignored) {
        }
        Log.info("'" + pageName + "' page existence verification. Exists = " + result);
        return result;
    }

}
