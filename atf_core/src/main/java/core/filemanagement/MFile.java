package core.filemanagement;

import java.io.*;
import java.util.ArrayList;


public class MFile{
	
	public static boolean exists(String filePath, int waitTime){
		long startTime = System.currentTimeMillis();
		boolean found=false;
		File f = new File(filePath);
		while(System.currentTimeMillis() - startTime<waitTime*1000){
			if (f.exists()){
				found = true;
				break;
			}
		}
		return found;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public static String[] read(String pathToFile){
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(pathToFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
		    String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

		    while (line != null) {
		    	list.add(line);
		        try {
					line = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String[] out = new String[list.size()];
		
		for (int i=0;i<list.size();i++){
			out[i]=list.get(i);
		}
		
		return out;
		
	}
	
}
