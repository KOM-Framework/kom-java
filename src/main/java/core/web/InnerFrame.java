package core.web;

public abstract class InnerFrame extends WebDynamicInit {
	
	protected abstract String webFrameId();
	
	public InnerFrame(){
		Browser.getDriver().switchTo().frame(webFrameId());
	}

}
