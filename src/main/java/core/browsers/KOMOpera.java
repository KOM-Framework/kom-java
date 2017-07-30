package core.browsers;

import io.github.bonigarcia.wdm.OperaDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;

public class KOMOpera {
    private WebDriver driver;
    public WebDriver getDriver(){
        return this.driver;
    }
    public KOMOpera(){
        OperaDriverManager.getInstance().setup();
        driver = new OperaDriver();
    }
}
