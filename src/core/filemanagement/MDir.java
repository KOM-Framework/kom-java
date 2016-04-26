package core.filemanagement;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.io.filefilter.DirectoryFileFilter;

public class MDir {

	public static String getLatestDirectory(String location){
		File parentDir = new File(location);
		ArrayList<String> directoryList = new ArrayList<String>(); 
		Arrays.asList(parentDir.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY)).forEach(dir -> directoryList.add(dir.getName()));
		Collections.reverse(directoryList);
		return directoryList.get(0);
	}
	
}
