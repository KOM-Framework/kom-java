package core.browsers;

import core.Global;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class RemoteBrowsers extends RemoteWebDriver{
    public  RemoteBrowsers(){
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
        try {
            new RemoteWebDriver(new URL(Global.SELENIUM_HUB),capability);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
