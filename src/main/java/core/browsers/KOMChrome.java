package core.browsers;


import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class KOMChrome{
    private WebDriver driver;
    public WebDriver getDriver(){
        return this.driver;
    }
    public KOMChrome(){
        ChromeDriverManager.getInstance().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions","--start-maximized","--allow-running-insecure-content",
                "--ignore-certificate-errors","--disable-print-preview","--test-type","--disable-web-security","--disk-cache-size=1",
                "--media-cache-size=1","--disable-infobars");
        this.driver = new ChromeDriver(options);
    }
}
