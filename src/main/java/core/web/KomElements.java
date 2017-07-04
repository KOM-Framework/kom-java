package core.web;

import core.utilities.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static core.Global.DEFAULT_EXPLICIT_WAIT;

public class KomElements {

    public By byId;

    public KomElements(By itemId){
        byId=itemId;
    }

    protected List<WebElement> getWebElements(int waitTime){
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitTime);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byId));
    }

    public List<KomElement> getElement(int... waitTime) {
        int waitValue = waitTime.length == 0 ? DEFAULT_EXPLICIT_WAIT : waitTime[0];
        List<WebElement> webList= this.getWebElements(waitValue);
        ArrayList<KomElement> outList = new ArrayList<>();
        int size = webList.size();
        for(int i=1;i<=size;i++){
            KomElement item = new KomElement(By.xpath("("+byId.toString().substring(byId.toString().indexOf(":") + 1, byId.toString().length()).trim()+")"+"["+i+"]"));
            outList.add(item);
        }
        return outList;
    }

    public String getLocator(){
        return byId.toString().substring(byId.toString().indexOf(":") + 1, byId.toString().length()).trim();
    }

    public boolean exists(int waitTime){
        Log.info("'"+byId.toString()+ "' existence verification.");
        boolean result = false;
        try{
            WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitTime);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byId));
            result = true;
        }
        catch (TimeoutException ignored){
        }
        Log.info(String.format("Exists = %s", result));
        return result;
    }

    public int getSize(){
        int out = this.getElement().size();
        Log.info("'"+byId.toString()+ "' list size verification. Size = "+out);
        return out;
    }

    public KomElement getListElement(int index){
        List<KomElement> tempItemList = getElement();
        KomElement outItem;
        if(tempItemList!=null&&tempItemList.size()>index){
            outItem=tempItemList.get(index);
            Log.info("Get element index-'"+index+"' from '"+byId.toString()+ "' list.");
            return outItem;
        }
        else{
            Log.info("Element index-'"+index+"' of '"+byId.toString()+ "' list is NOT found.");
            return null;
        }
    }

    public KomElement getListElement(String text)
    {
        List<KomElement> tempItemList = getElement();
        if(tempItemList!=null&&tempItemList.size()>0){
            for(KomElement outItem: tempItemList){
                if(outItem.getText().trim().equals(text)){
                    Log.info("Get element text-'"+text+"' from '"+byId.toString()+ "' list.");
                    return outItem;
                }
            }
        }
        Log.info("Element text-'"+text+"' of '"+byId.toString()+ "' list is NOT found.");
        return null;
    }

    public KomElement getListElementContainingText(String text){
        List<KomElement> tempItemList = getElement();
        if(tempItemList!=null&&tempItemList.size()>0){
            for(KomElement outItem: tempItemList){
                if(outItem.getText().trim().contains(text)){
                    Log.info("Get element text-'"+text+"' from '"+byId.toString()+ "' list.");
                    return outItem;
                }
            }
        }
        Log.info("Element text-'"+text+"' of '"+byId.toString()+ "' list is NOT found.");
        return null;
    }

    public ArrayList<String> getAllItemsText() {
        Log.info("Getting item text from the '" + byId.toString() + "' item list");
        ArrayList<String> outList = new ArrayList<String>();

        List<KomElement> tempItemList = getElement();
        if(tempItemList!=null&&tempItemList.size()>0){
            for(KomElement item: tempItemList){
                outList.add(item.getText());
            }
        }
        return outList;
    }

    public boolean allRowContainText(String textToCheck)
    {
        List<KomElement> tempItemList = getElement();
        boolean allContains = true;
        for(int i=1; i<tempItemList.size(); i++)
        {
            if(!tempItemList.get(i).getText().contains(textToCheck))
            {
                allContains=false;
                Log.info("Row-"+(i-1)+" of '"+byId.toString()+ "' list does noe contains text-"+textToCheck+".");
                break;
            }
        }
        if(allContains)
            Log.info("All rows of '"+byId.toString()+ "' list contains text-"+textToCheck+".");
        return allContains;
    }

    protected void waitForRefreshed(String initialElementId, long waitTime) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        long startTime = System.currentTimeMillis();
        long currentTime;
        waitTime*=1000;
        do{
            String elementId = this.getListElement(0).getElementId();
            if (!initialElementId.equals(elementId)){
                Log.info(String.format("FindTable is refreshed. Current elementId=%s, initialId=%s", elementId, initialElementId));
                break;
            }else {
                Log.info("Waiting for the List to be refreshed");
            }
            currentTime = System.currentTimeMillis()-startTime;
        }while(currentTime<waitTime);
    }
}