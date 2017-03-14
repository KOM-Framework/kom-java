package core.xls;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class XlsParser {

	private static HSSFWorkbook hssfWorkbook;

	public static ArrayList<String> getColumnValues(String xlsFile, String sheetName, String columnName)
			throws IOException {
		ArrayList<String> outList = new ArrayList<String>();
		File excel = new File(xlsFile);
		FileInputStream fis = new FileInputStream(excel);
		hssfWorkbook = new HSSFWorkbook(fis);
		
		HSSFSheet ws;
		if(!sheetName.isEmpty())
			ws = hssfWorkbook.getSheet(sheetName);
		else
			ws =  hssfWorkbook.getSheetAt(0);
		
		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();
		for (int i = 0; i < colNum; i++) {
			HSSFRow row = ws.getRow(0);
			HSSFCell cell = row.getCell(i);
			String value = cell.toString();
			if (columnName.equals(value)) {
				for (int j = 1; j < rowNum; j++) {
					row = ws.getRow(j);
					if(cell!=null)
						cell.setCellType(1);
					
					cell = row.getCell(i);
					DataFormatter df = new DataFormatter();
					value = df.formatCellValue(cell);
					outList.add(value);
				}
			}
		}
		return outList;
	}

	@SuppressWarnings("rawtypes")
	public static ArrayList<XLSColumnsData> getColumnsValues(String xlsFile, String sheetName, String[] columnNames)
			throws IOException {
		ArrayList<String> readColumnNames;

		if (columnNames == null) {
			readColumnNames = new ArrayList<String>();
			File excel = new File(xlsFile);
			FileInputStream fis = new FileInputStream(excel);
			hssfWorkbook = new HSSFWorkbook(fis);
			HSSFSheet ws = hssfWorkbook.getSheet(sheetName);
			int colNum = ws.getRow(0).getLastCellNum();
			HSSFRow row = ws.getRow(0);
			for (int i = 0; i < colNum; i++) {
				HSSFCell cell = row.getCell(i);
				String value = cell.toString();
				readColumnNames.add(value);
			}
		} else {
			readColumnNames = new ArrayList<String>(Arrays.asList(columnNames));
		}

		ArrayList<ArrayList<String>> columnsData = new ArrayList<ArrayList<String>>();
		ArrayList<XLSColumnsData> columnsValues = new ArrayList<XLSColumnsData>();

		for (String columnName : readColumnNames) {
			ArrayList<String> columnData = getColumnValues(xlsFile, sheetName, columnName);
			columnsData.add(columnData);
		}
		for (int j = 0; j < columnsData.get(0).size(); j++) {
			XLSColumnsData<String> cellData = new XLSColumnsData<String>();
			for (int i = 0; i < columnsData.size(); i++) {
				cellData.ListValues.add(columnsData.get(i).get(j));
			}
			columnsValues.add(cellData);
		}
		// Debug purposes
		/*
		 * for (int i=0;i<culumsValues.size();i++){
		 * System.out.println(culumsValues.get(i).ListValues); }
		 */
		return columnsValues;
	}
	
	@SuppressWarnings("rawtypes")
	public static Object[][] getDataProviderFromXLS(String xlsFile, String xlsSheet){
		ArrayList<XLSColumnsData> data = null;
		try {
			data = XlsParser.getColumnsValues(xlsFile, xlsSheet, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int size = data.size();
		Object[][] returnList = new Object[size][];
		for(int i=0;i<size;i++){
			returnList[i]=data.get(i).ListValues.toArray();
			
		}
		return returnList;
	}
	
	public static void main(String[] args) throws IOException {
	}
	

}
