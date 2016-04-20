package core.xls;

import java.util.ArrayList;

public class XLSColumsData<Type> {
		
	public ArrayList<Type> ListValues = new ArrayList<Type>();

	public static boolean equals(XLSColumsData<?> actualData, XLSColumsData<?> expectedData) {
		boolean out = true;
		for (int i = 0; i < actualData.ListValues.size(); i++) {
			if (!actualData.ListValues.get(i).equals(expectedData.ListValues.get(i))) {
				out = false;
				break;
			}
		}
		return out;
	}

	@SuppressWarnings("rawtypes")
	public static ArrayList<XLSColumsData> formatXLSData(ArrayList<XLSColumsData> inputData) {
		ArrayList<XLSColumsData> outData = new ArrayList<XLSColumsData>();
		for (int i = 0; i < inputData.size(); i++) {
			XLSColumsData<String> formattedData = new XLSColumsData<String>();
			for (int j = 0; j < inputData.get(i).ListValues.size(); j++) {
				StringBuilder cellValue = new StringBuilder(inputData.get(i).ListValues.get(j).toString());
				for (int index = 0; index < cellValue.length(); index++) {
					if (cellValue.charAt(index) == ' ') {
						cellValue.deleteCharAt(index);
					}
				}
				while (cellValue.charAt(0) == '0') {
					cellValue.deleteCharAt(0);
				}
				formattedData.ListValues.add(cellValue.toString());
			}
			outData.add(formattedData);
		}
		return outData;
	}

	@SuppressWarnings("rawtypes")
	public static ArrayList<ArrayList<XLSColumsData>> verifyXLSDataLists(ArrayList<XLSColumsData> actualList,
			ArrayList<XLSColumsData> expectedList) {
		ArrayList<ArrayList<XLSColumsData>> out = new ArrayList<ArrayList<XLSColumsData>>();
		for (int i = 0; i < actualList.size(); i++) {
			for (int j = 0; j < expectedList.size(); j++) {
				if (equals(actualList.get(i), expectedList.get(j))) {
					actualList.remove(i);
					i--;
					expectedList.remove(j);
					j--;
					break;
				}
			}
		}
		out.add(actualList);
		out.add(expectedList);
		return out;
	}
	
}
