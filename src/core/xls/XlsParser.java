package core.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;

public class XlsParser {

	private static HSSFWorkbook hssfWorkbook;

	public static ArrayList<String> getColumValues(String xlsFile, String sheetName, String columName)
			throws IOException {
		ArrayList<String> outList = new ArrayList<String>();
		File excel = new File(xlsFile);
		FileInputStream fis = new FileInputStream(excel);
		hssfWorkbook = new HSSFWorkbook(fis);

		HSSFSheet ws;
		if (!sheetName.isEmpty())
			ws = hssfWorkbook.getSheet(sheetName);
		else
			ws = hssfWorkbook.getSheetAt(0);

		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();
		for (int i = 0; i < colNum; i++) {
			HSSFRow row = ws.getRow(0);
			HSSFCell cell = row.getCell(i);
			String value = cell.toString();
			if (columName.equals(value)) {
				for (int j = 1; j < rowNum; j++) {
					row = ws.getRow(j);
					if (cell != null)
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
	public static ArrayList<XLSColumsData> getColumsValues(String xlsFile, String sheetName, String[] columNames)
			throws IOException {
		ArrayList<String> readColumNames;

		if (columNames == null) {
			readColumNames = new ArrayList<String>();
			File excel = new File(xlsFile);
			FileInputStream fis = new FileInputStream(excel);
			hssfWorkbook = new HSSFWorkbook(fis);
			HSSFSheet ws = hssfWorkbook.getSheet(sheetName);
			int colNum = ws.getRow(0).getLastCellNum();
			HSSFRow row = ws.getRow(0);
			for (int i = 0; i < colNum; i++) {
				HSSFCell cell = row.getCell(i);
				String value = cell.toString();
				readColumNames.add(value);
			}
		} else {
			readColumNames = new ArrayList<String>(Arrays.asList(columNames));
		}

		ArrayList<ArrayList<String>> columsData = new ArrayList<ArrayList<String>>();
		ArrayList<XLSColumsData> culumsValues = new ArrayList<XLSColumsData>();

		for (String columName : readColumNames) {
			ArrayList<String> columData = getColumValues(xlsFile, sheetName, columName);
			columsData.add(columData);
		}
		for (int j = 0; j < columsData.get(0).size(); j++) {
			XLSColumsData<String> cellData = new XLSColumsData<String>();
			for (int i = 0; i < columsData.size(); i++) {
				cellData.ListValues.add(columsData.get(i).get(j));
			}
			culumsValues.add(cellData);
		}
		// Debug purposes
		/*
		 * for (int i=0;i<culumsValues.size();i++){
		 * System.out.println(culumsValues.get(i).ListValues); }
		 */
		return culumsValues;
	}

	@SuppressWarnings("rawtypes")
	public static Object[][] getDataProviderFromXLS(String xlsFile, String xlsSheet) {
		ArrayList<XLSColumsData> data = null;
		try {
			data = XlsParser.getColumsValues(xlsFile, xlsSheet, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int size = data.size();
		Object[][] returnList = new Object[size][];
		for (int i = 0; i < size; i++) {
			returnList[i] = data.get(i).ListValues.toArray();

		}
		return returnList;
	}

}
