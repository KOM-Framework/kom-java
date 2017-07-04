package core.datatypes;

import core.utilities.Log;
import core.utilities.Reflect;
import core.web.Browser;
import core.web.KomElements;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static core.Global.DEFAULT_AJAX_WAIT;
import static core.Global.DEFAULT_EXPLICIT_WAIT;

public class Table extends KomElements {

    public Pager pager;
    private Object structure;

    public Table(String xpath, Class structure) throws IllegalAccessException, InstantiationException {
        super(By.xpath(xpath));
        this.structure = structure.newInstance();
    }

    public Table(String xpath, Class structure, String pagerXpath, String currentPageXpath, Pager.PagerType pagerType) throws IllegalAccessException, InstantiationException {
        super(By.xpath(xpath));
        this.structure = structure.newInstance();
        this.pager = new Pager(By.xpath(pagerXpath), By.xpath(currentPageXpath));
        this.pager.setPagerType(pagerType);
    }

    @SuppressWarnings("unchecked")
    public <C> ArrayList<C> getItems() throws NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Log.info("Getting items from the '" + this.byId.toString() + "' grid view");
        ArrayList<C> elementsList = new ArrayList<>();
        Field[] fields = this.structure.getClass().getDeclaredFields();
        if (this.exists(DEFAULT_EXPLICIT_WAIT)){
            int size = this.getSize();
            for (int i = 0; i < size; i++) {
                Object item = null;
                try {
                    item = this.structure.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                for (Field f : fields) {
                    if (f.getModifiers() == 1) {
                        try {
                            Object fieldsObject = f.get(item);
                            Reflect.invokeMethod(fieldsObject, "setBaseList", this);
                            Reflect.invokeMethod(fieldsObject, "setIndexInBaseList", i);
                            f.set(item, fieldsObject);
                        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
                            e.printStackTrace();
                        }
                    }
                }
                elementsList.add((C) item);
            }
        }
        return elementsList;
    }

    /*
     * this method is used to get list's row by row's field text.
     */
    public <C> C getItem(String itemName, String fieldBy) throws NoSuchMethodException, InvocationTargetException, NoSuchFieldException, IllegalAccessException {
        Log.info("Trying to find an '" + itemName + "' item in '" + this.byId.toString()+ "' grid view");
        C foundItem = null;
        if (pager != null) {
            this.switchToTheFirstPage();
        }

        boolean process = true;
        do {
            ArrayList<C> itemList = this.getItems();
            for (C item : itemList) {
                String fieldValue = null;
                Object fieldObject = null;
                fieldObject = Reflect.invokeMethodFromField(item, fieldBy,
                        "getText");
                fieldValue = fieldObject == null ? "" : (String) fieldObject;
                if (fieldValue.equals(itemName)) {
                    foundItem = item;
                    break;
                }
            }
            if (foundItem != null) {
                process = false;
            } else if (pager != null && this.switchToTheNextPage()) {
                Log.info("Switching to the next page");
            } else {
                process = false;
            }
        } while (process);

        return foundItem;
    }

    public <C> C getItem(int index) throws NoSuchMethodException, InvocationTargetException, NoSuchFieldException, IllegalAccessException {
        Log.info("Trying to get an item by index " + index + "in '" + this.byId.toString() + "' grid view");
        C foundItem = null;
        if (this.pager != null) {
            this.switchToTheFirstPage();
        }
        ArrayList<C> itemList = this.getItems();
        int size = itemList.size();

        if (index >= 0 && index < size) {
            foundItem = itemList.get(index);
        }
        else if (this.pager != null){
            int page = (index / size)+1;//TODO: Delete +1 if pager starts from 0
            int indexOnPage = index % size;
            int pageCount = this.pager.getSize();
            if(page<=pageCount){
                this.pager.moveToPage(page);
                itemList = this.getItems();
                size = itemList.size();
                if (indexOnPage >= 0 && indexOnPage < size) {
                    foundItem = itemList.get(indexOnPage);
                }
            }
        }
        if (foundItem==null)
            Log.info("Index-'" + index + 1 + "' in '" + this.byId.toString() + "' chart list is NOT valid.");
        return foundItem;
    }

    public <C> ArrayList<String> getAllItemNames(String fieldBy) throws NoSuchMethodException, InvocationTargetException, NoSuchFieldException, IllegalAccessException {
        Log.info("Getting item names from the '" + this.byId.toString() + "' grid view");
        ArrayList<String> outList = new ArrayList<>();
        if (pager != null) {
            this.switchToTheFirstPage();
        }
        boolean process = true;
        do {
            Browser.sleep(200);
            ArrayList<C> itemList = this.getItems();
            for (C item : itemList) {
                String fieldValue = "";
                Object fieldObject = null;
                fieldObject = Reflect.invokeMethodFromField(item, fieldBy,
                        "getText");
                fieldValue = fieldObject == null ? "" : (String) fieldObject;
                outList.add(fieldValue);
            }
            if (process){
                if (pager != null && this.switchToTheNextPage()) {
                    Log.info("Switching to the next page");
                } else {
                    process = false;
                }
            }
        } while (process);

        return outList;
    }

    public int getListLength() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Log.info("Trying to find list length in '" + this.byId.toString() + "' grid view");
        int outCount = 0;
        if (this.pager != null)
            this.switchToTheFirstPage();
        do{
            outCount += this.getSize();
            Log.info("Switching to the next page");
        }while (pager != null && this.switchToTheNextPage());
        return outCount;
    }

    private boolean switchToTheNextPage() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        String currentElementId = this.getListElement(0).getElementId();
        boolean out = pager.nextPage();
        if (out){
            this.waitForRefreshed(currentElementId, DEFAULT_AJAX_WAIT);
        }
        return out;
    }

    private void switchToTheFirstPage() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        String currentElementId = this.getListElement(0).getElementId();
        if (!this.pager.firstPage()){
            this.waitForRefreshed(currentElementId, DEFAULT_AJAX_WAIT);
        }
    }

}