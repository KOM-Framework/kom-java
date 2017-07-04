package core.web;

import core.utilities.Log;

/**
 * Created by olehk on 31/03/2017.
 */
public abstract class WebFrame extends BasePage{

    protected abstract void closeActions() throws Exception;

    protected WebFrame() {
        super();
    }

    public final void close() throws Exception {
        if (exists()) {
            Log.info(String.format("Closing %s web frame", pageName));
            closeActions();
        }
    }

}
