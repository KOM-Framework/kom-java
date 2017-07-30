package core.browsers;


import io.github.bonigarcia.wdm.EdgeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class KOMEdge {
    private WebDriver driver;
    public WebDriver getDriver(){
        return this.driver;
    }
    public KOMEdge(){
        EdgeDriverManager.getInstance().setup();
        driver = new EdgeDriver();

    }
}
