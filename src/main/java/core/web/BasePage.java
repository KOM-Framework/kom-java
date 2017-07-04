package core.web;

import core.annotations.AnnotationsSupport;
import core.utilities.Log;
import core.utilities.Reflect;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.annotation.Annotation;

/**
 * Created by olehk on 31/03/2017.
 */
abstract class BasePage extends WebDynamicInit {

    String pageName = this.getClass().getName();
    private By pageLocator;

    boolean exists(int... waitTime) throws Exception {
        if (pageLocator == null){
            throw new Exception(String.format("Unable to find page locator. Please check if @FindBy annotation is specified for the %s page", pageName));
        }
        int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
        boolean result = false;
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitValue);
        try {
            WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(pageLocator));
            result = item != null;
        } catch (TimeoutException ignored) {
        }
        Log.info("'" + pageName + "' page existence verification. Exists = " + result);
        return result;
    }

    void setPageLocator(){
        Annotation a = Reflect.getClassAnnotation(this.getClass(), "FindBy");
        pageLocator = AnnotationsSupport.buildBy(a);
    }
}
