package core.datatypes;

import core.web.Browser;
import core.web.WebItem;
import core.web.WebItemList;
import org.apache.commons.lang.math.NumberUtils;
import org.openqa.selenium.By;

public class Pager extends WebItemList{

	private WebItem currentPage;
	private PagerType type;
	
	public enum PagerType {
		STANDARD, WITH_NEXT_PAGE, WITH_NEXT_VIEW
	}
	
	public Pager(By itemId) {
		super(itemId);
	}
	
	public Pager(By itemId, By currentPage) {
		super(itemId);
		this.currentPage = new WebItem(currentPage);
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
		WebItem w;
		
		switch (type) {
		case STANDARD:
			w = getListElementExact(pageNumberStr);
			if (w != null) {
				w.moveToAndClick();
				return true;
			} else {
				return false;
			}
			
		case WITH_NEXT_PAGE:
			do {
				w = getListElementExact(pageNumberStr);
				if (w != null) {
					w.moveToAndClick();
					return true;
				} else if (!(upwards ? clickShowLaterButton() : clickShowEarlierButton())) {
					return false;
				}
			} while (true);
			
		case WITH_NEXT_VIEW:
			do {
				w = getListElementExact(pageNumberStr);
				if (w != null) {
					w.moveToAndClick();
					return true;
				} else if (!(upwards ? clickNextPageButton() : clickPreviousPageButton())) {
					return false;
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
		WebItem w;
		
		switch (type) {
		case STANDARD:
			w = getListElementExact(pageNumber);
			if (w != null) {
				w.moveToAndClick();
				return true;
			} else {
				return false;
			}
			
		case WITH_NEXT_PAGE:
			return upwards ? clickNextPageButton() : clickPreviousPageButton();
			
		case WITH_NEXT_VIEW:
			w = getListElementExact(pageNumber);
			if (w != null) {
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
			return Integer.parseInt(currentPage.getText());
		} else {
			return -1;
		}
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
		
		WebItem w = getListElement(index);
		
		if (!NumberUtils.isNumber(w.getText())) {
			w.moveToAndClick();
			Browser.sleep(1000);
			return true;
		} else {
			return false;
		}
	}

}
