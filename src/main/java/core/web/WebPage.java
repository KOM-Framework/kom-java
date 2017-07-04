package core.web;

import core.Global;
import core.utilities.Log;
import core.utilities.Reflect;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class WebPage extends BasePage {

    protected ArrayList<String> invokeArgs;
    protected abstract void invokeActions() throws Exception;

    protected WebPage(String... invokeArgs){
        super();
        this.invokeArgs = new ArrayList<>(Arrays.asList(invokeArgs));
        this.setPageLocator();
    }

	public final void invoke() throws Exception {
		if (!exists()) {
			Log.info("Invoking '" + pageName + "' page");
			invokeActions();
			Assert.assertTrue(exists(Global.DEFAULT_PAGE_LOAD_TIME), String.format("%s does not exists after invoke attempt in %s seconds", pageName, Global.DEFAULT_PAGE_LOAD_TIME));
		}
	}

}
