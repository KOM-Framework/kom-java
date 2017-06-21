package core.datatypes;

import core.Log;
import core.web.WebItem;
import org.openqa.selenium.By;

/**
 * Created by olehk on 07/04/2017.
 */
public class WebLink extends WebItem {

    public WebLink(By locator) {
        super(locator);
    }

    public String getLink(){
        Log.info(String.format("Getting link from the %s link", this.byId.toString()));
        return this.getActiveItem().getAttribute("href");
    }

}
