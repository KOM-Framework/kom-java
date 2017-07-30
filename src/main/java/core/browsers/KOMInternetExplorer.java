package core.browsers;

import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class KOMInternetExplorer {
    private WebDriver driver;
    public WebDriver getDriver(){
        return this.driver;
    }
    public KOMInternetExplorer(){
        InternetExplorerDriverManager.getInstance().setup();
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        driver = new InternetExplorerDriver(capabilities);
    }
}
