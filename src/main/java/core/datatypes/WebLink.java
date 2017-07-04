package core.datatypes;

import core.utilities.Log;
import core.web.KomElement;
import org.openqa.selenium.By;

/**
 * Created by olehk on 07/04/2017.
 */
public class WebLink extends KomElement {

    public WebLink(By locator) {
        super(locator);
    }

    public String getLink(){
        Log.info(String.format("Getting link from the %s link", this.byId.toString()));
        return this.getActiveItem().getAttribute("href");
    }

}
