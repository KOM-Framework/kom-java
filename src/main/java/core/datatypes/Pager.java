package core.datatypes;

import core.utilities.Log;
import core.web.Browser;
import core.web.KomElement;
import core.web.KomElements;
import org.apache.commons.lang.math.NumberUtils;
import org.openqa.selenium.By;

public class Pager extends KomElements {

    private KomElement currentPage;
    private PagerType type;

    public enum PagerType {
        STANDARD, WITH_NEXT_PAGE, WITH_NEXT_VIEW
    }

    public Pager(By itemId) {
        super(itemId);
    }

    public Pager(By itemId, By currentPage) {
        super(itemId);
        this.currentPage = new KomElement(currentPage);
    }

    public void setPagerType(PagerType type) {
        this.type = type;
    }

    public boolean firstPage() {
        return moveToPage(1);
    }

    public boolean nextPage() {
        return moveOnePage(true);
    }

    public boolean previousPage() {
        return moveOnePage(false);
    }

    public boolean moveToPage(int pageNumber) {
        int currentPage = getCurrentPage();

        if (pageNumber == currentPage) {
            return true;
        } else if (pageNumber < 1 || currentPage < 1 || getSize() == 0) {
            return false;
        }

        String pageNumberStr = String.valueOf(pageNumber);
        boolean upwards = pageNumber > currentPage;
        KomElement w;

        switch (type) {
            case STANDARD:
                w = getListElement(pageNumberStr);
                if (w != null) {
                    w.moveToAndClick();
                    return false;
                } else {
                    return true;
                }

            case WITH_NEXT_PAGE:
                do {
                    w = getListElement(pageNumberStr);
                    if (w != null) {
                        Log.info("Switching to the next page");
                        w.moveToAndClick();
                        return false;
                    } else if (!(upwards ? clickShowLaterButton() : clickShowEarlierButton())) {
                        return true;
                    }
                } while (true);

            case WITH_NEXT_VIEW:
                do {
                    w = getListElement(pageNumberStr);
                    if (w != null) {
                        Log.info("Switching to the next page");
                        w.moveToAndClick();
                        return false;
                    } else if (!(upwards ? clickNextPageButton() : clickPreviousPageButton())) {
                        return true;
                    }
                } while (true);
        }

        return false;
    }

    private boolean moveOnePage(boolean upwards) {
        if (getCurrentPage() < 1) {
            return false;
        }

        String pageNumber = String.valueOf(getCurrentPage() + (upwards ? 1 : -1));
        KomElement w;

        switch (type) {
            case STANDARD:
                w = getListElement(pageNumber);
                if (w != null) {
                    Log.info("Switching to the next page");
                    w.moveToAndClick();
                    return true;
                } else {
                    return false;
                }

            case WITH_NEXT_PAGE:
                return upwards ? clickNextPageButton() : clickPreviousPageButton();

            case WITH_NEXT_VIEW:
                w = getListElement(pageNumber);
                if (w != null) {
                    Log.info("Switching to the next page");
                    w.moveToAndClick();
                    return true;
                } else {
                    return upwards ? clickNextPageButton() : clickPreviousPageButton();
                }
        }

        return false;
    }

    private int getCurrentPage() {
        if (currentPage.exists(0)) {
            String actualText = currentPage.getText();
            return actualText.isEmpty() ? 0 : Integer.parseInt(actualText);
        }
        return -1;
    }

    private boolean clickPreviousPageButton() {
        return clickNavigationButton(0);
    }

    private boolean clickNextPageButton() {
        return clickNavigationButton(getSize() - 1);
    }

    private boolean clickShowEarlierButton() {
        return clickNavigationButton(2);
    }

    private boolean clickShowLaterButton() {
        return clickNavigationButton(getSize() - 3);
    }

    private boolean clickNavigationButton(int index) {
        if (index < 0 || index >= getSize()) {
            return false;
        }
        KomElement w = getListElement(index);
        if (!NumberUtils.isNumber(w.getText())) {
            w.moveToAndClick();
            Browser.sleep(1000);
            return true;
        } else {
            return false;
        }
    }

}