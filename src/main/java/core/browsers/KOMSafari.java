package core.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class KOMSafari {
    private WebDriver driver;
    public WebDriver getDriver(){
        return this.driver;
    }
    public KOMSafari(){
        driver = new SafariDriver();
    }
}
