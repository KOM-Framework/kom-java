package core.utilities;

import org.openqa.selenium.By;

import java.util.ArrayList;

/**
 * Created by olehk on 07/04/2017.
 */
public class General {

    public static String getMiddleValue(String originalString, String startSubStr, String endSubStr){
        String out = originalString.substring(originalString.lastIndexOf(startSubStr)+startSubStr.length(), originalString.indexOf(endSubStr));;
        return out;
    }

    public static ArrayList<By> byChainedToByList(Object id){
        String tempString = id.toString();
        tempString = General.getMiddleValue(tempString, "By.chained({", "})");
        String[] arr = tempString.split(",By.");
        ArrayList<By> out = new ArrayList<>();
        for(String idString:arr){
            String expression =idString.substring(idString.indexOf(":")+1, idString.length()).trim();
            idString = idString.substring(0,idString.indexOf(":")).trim().replace("By.", "");
            switch(idString) {
                case "xpath":
                    out.add(By.xpath(expression));
                    break;
                case "id":
                    out.add(By.id(expression));
                    break;
            }
        }
        return out;
    }

    public enum OrderType {
        ASC, DESC
    }

    public static boolean compareListStringOrder(ArrayList<String> resultList, OrderType orderType){
        boolean compareResult=false;
        if (resultList.size()==0 | resultList.size()==1) {
            compareResult = true;
        }else {
            for (int i = 0; i < resultList.size() - 1; i++) {
                String currentString = resultList.get(i);
                String nextString = resultList.get(i + 1);
                if (currentString.isEmpty() && nextString.isEmpty()) {
                    compareResult = true;
                    i++;
                }
                if (!currentString.isEmpty() && currentString != null) {
                    if (!nextString.isEmpty() && nextString != null) {
                        switch (orderType) {
                            case ASC:
                                compareResult = nextString.toLowerCase().compareTo(currentString.toLowerCase()) >= 0;
                                break;
                            case DESC:
                                compareResult = nextString.toLowerCase().compareTo(currentString.toLowerCase()) <= 0;
                                break;
                        }
                        if (!compareResult) {
                            Log.info(String.format("comparing results with %s order, current value: %s with next value: %s", orderType, currentString, nextString));
                            break;
                        }
                    }
                }
            }
        }
        return compareResult;
    }

    public static boolean compareNumericListOrder(ArrayList<String> resultList, OrderType orderType){
        boolean compareResult=false;
        if (resultList.size()==0 | resultList.size()==1) {
            compareResult = true;
        }else {
            for (int i = 0; i < resultList.size() - 1; i++) {
                String currentString = resultList.get(i).replaceAll("\\s", "").replace(".", "").replace(",", "");
                String nextString = resultList.get(i+1).replaceAll("\\s", "").replace(".", "").replace(",", "");
                if (currentString.isEmpty() && nextString.isEmpty()) {
                    compareResult = true;
                    i++;
                }
                if (!currentString.isEmpty() && currentString != null) {
                    Long currentValue = Long.valueOf(currentString);
                    if (!currentString.isEmpty() && nextString != null) {
                        Long nextValue = Long.valueOf(nextString);
                        switch (orderType) {
                            case ASC:
                                compareResult = nextValue >= currentValue;
                                break;
                            case DESC:
                                compareResult = nextValue <= currentValue;
                                break;
                        }
                        if (!compareResult) {
                            Log.info(String.format("comparing results with %s order, current value: %s with next value: %s", orderType, currentString, nextString));
                            break;
                        }
                    }
                }
            }
        }
        return compareResult;
    }

}
