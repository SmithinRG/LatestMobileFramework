package com.mobileautomationframework.datadriven;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Properties;

	import org.apache.poi.ss.format.CellTextFormatter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
	import org.apache.poi.xssf.usermodel.XSSFRow;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.testng.annotations.BeforeTest;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;


	public class DataDriven  {

		private static XSSFSheet ExcelWSheet;	 
		private static XSSFWorkbook ExcelWBook;
		private static XSSFCell Cell;
		private static XSSFRow Row;
		private static Properties property;
		public static Map map;
		public static String strDeviceName = null;
		public static String strDeviceUDID = null;
		public static int intRow = 0;
		public static Map mapDevice;
		public static Map mapdata;
		public static String projectPath = System.getProperty("user.dir");

		//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method    
		public static void setExcelFile(String Path,String SheetName) throws Exception {
			try {
				// Open the Excel file
				FileInputStream ExcelFile = new FileInputStream(Path);
				// Access the required test data sheet
				ExcelWBook = new XSSFWorkbook(ExcelFile);
				ExcelWSheet = ExcelWBook.getSheet(SheetName);
			} catch (Exception e){			
				throw (e);			
			}
		}


		public static void getDeviceDetails(String strExcelPath, String strSheetName) {
			try {
				setExcelFile(strExcelPath,strSheetName);
				String strDeviceName = ExcelWSheet.getRow(0).getCell(0).getStringCellValue();
				String strDeviceUDID = ExcelWSheet.getRow(1).getCell(0).getStringCellValue();

			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}


		public static Map getDeviceDetail(String strExcelPath, String strSheetName) {
			mapDevice = null;
			try {
				setExcelFile(strExcelPath,strSheetName);
				int col = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
				//			System.out.println("No of COlumns "  + col);
				String key=null;
				String value = null;
				mapDevice = new HashMap<String,String>();
				for(int i=0;i<col;i++) {
					key = ExcelWSheet.getRow(0).getCell(i).getStringCellValue();
					value = ExcelWSheet.getRow(1).getCell(i).getStringCellValue();
					//				System.out.println(key + "-----" + value);
					mapDevice.put(key, value);
				}
				return mapDevice;

			}catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		}

		public static Map getCellData() {
			map = null;
			try {
				map = new HashMap<String,String>();
				int col = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
				for(int i=0;i<col;i++) {
					String key = ExcelWSheet.getRow(0).getCell(i).getStringCellValue();
					String value = "";
					if(ExcelWSheet.getRow(1).getCell(i).getCellType() == CellType.NUMERIC) 
						value = String.valueOf(ExcelWSheet.getRow(1).getCell(i).getNumericCellValue());
					else
						value = ExcelWSheet.getRow(1).getCell(i).getStringCellValue();
					map.put(key,value); 
				}
				return map;
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return map;
			}
		}


		public static Map getTestData4(String txtvalue) {
			try {

				Properties property = new Properties();
				property.load(new FileInputStream(System.getProperty("user.dir") + "\\config.properties"));
				setExcelFile(property.getProperty("TestDataPath"), property.getProperty("TestDataSheetName"));
				int row = ExcelWSheet.getPhysicalNumberOfRows();
				int col = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
				for(int i=1;i<row;i++) {
					Map mappy = new HashMap<String,String>();
					String value = null;
					String key = null;                      
					if(ExcelWSheet.getRow(i).getCell(2).getStringCellValue().equalsIgnoreCase(txtvalue)) {
						for(int j=0;j<col;j++) {
							Cell = ExcelWSheet.getRow(i).getCell(j);
							key = ExcelWSheet.getRow(0).getCell(j).getStringCellValue();
							if(Cell == null || Cell.getCellType() == CellType.BLANK)     
								value = "";
							else if(Cell.getCellType() == CellType.NUMERIC) 
								value = String.valueOf(Cell.getNumericCellValue());
							else
								value = Cell.getStringCellValue();
							mappy.put(key, value);
						}      
						return mappy;
					}                                
				}
				return null;

			}catch(Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e.getCause());
				e.printStackTrace();
				return null;              
			}




		}


		//////////////////Map Data//////////////////


		public static Map getCellMapData() {
			mapdata = null;
			try {
				mapdata = new HashMap<String,String>();
				int col = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
				for(int i=0;i<col;i++) {
					String key = ExcelWSheet.getRow(0).getCell(i).getStringCellValue();
					String value = "";
					if(ExcelWSheet.getRow(1).getCell(i).getCellType() == CellType.NUMERIC) 
						value = String.valueOf(ExcelWSheet.getRow(1).getCell(i).getNumericCellValue());
					else
						value = ExcelWSheet.getRow(1).getCell(i).getStringCellValue();
					mapdata.put(key,value); 
				}
				return mapdata;
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return mapdata;
			}
		}











		////------Iteration---


		//For Data Iteration
		public static int getRowCount() {
			int rowCount=0;
			try {

				rowCount = ExcelWSheet.getPhysicalNumberOfRows();
				System.out.println("Row= "+rowCount);

			}catch(Exception e) {
				e.printStackTrace();
				e.getCause();
				e.getMessage();
			}
			return rowCount;
		}
		public static String getCellData(int rowNum, int colNum) {
			String cellData=null;
			try {

				cellData = ExcelWSheet.getRow(rowNum).getCell(colNum).getStringCellValue();
				//System.out.println(cellData);

			}catch(Exception e) {
				e.printStackTrace();
				e.getMessage();
			}
			return cellData;
		}
		public static int getColCount() {
			int colCount=0;
			try {

				colCount = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
				System.out.println("Col= "+colCount);

			}catch(Exception e) {
				e.printStackTrace();
				e.getCause();
				e.getMessage();
			}
			return colCount;
		}
		
		
		///dataprovider
		@DataProvider(name = "signindata")
		public Object getDataSignIn() throws Exception {
			//String projectPath = System.getProperty("user.dir");
			//System.out.println(projectPath);
			//	ExcelUtility excel = new ExcelUtility(projectPath+"/excel/TestDataSheet.xlsx", "Sheet2");

			Object data= testData(projectPath+"/src/com/share/testdata/TestDataSheet.xlsx", "IterationSign");
			//Object data= testData("/Users/thi1907501/eclipse-workspace/ShareAppV2iOS/src/com/share/testdata/TestDataSheet.xlsx", "IterationSign");
			return data;

		}


		@DataProvider(name = "registrationdatayear")
		public Object getDataYear() throws Exception {
			//String projectPath = System.getProperty("user.dir");
			//System.out.println(projectPath);
			//	ExcelUtility excel = new ExcelUtility(projectPath+"/excel/TestDataSheet.xlsx", "Sheet2");

			Object data= testData(projectPath+"/src/com/share/testdata/TestDataSheet.xlsx", "IterationYear");
			//Object data= testData("/Users/thi1907501/eclipse-workspace/ShareAppV2iOS/src/com/share/testdata/TestDataSheet.xlsx", "IterationYear");
			return data;

		}

		@DataProvider(name = "registrationdata")
		public Object getData() throws Exception {
			//String projectPath = System.getProperty("user.dir");
			//	ExcelUtility excel = new ExcelUtility(projectPath+"/excel/TestDataSheet.xlsx", "Sheet2");
			Object data= testData(projectPath+"/src/com/share/testdata/TestDataSheet.xlsx", "Iteration");
			//Object data= testData("/Users/thi1907501/eclipse-workspace/ShareAppV2iOS/src/com/share/testdata/TestDataSheet.xlsx", "Iteration");
			return data;

		}

		public Object testData(String exPath,String shName) throws Exception {

			setExcelFile(exPath,shName);
			int rowCount = getRowCount();
			int colCount = getColCount();

			Object data[][]= new Object[rowCount-1][colCount];

			for(int i=1;i<rowCount;i++) {
				for(int j=0;j<colCount;j++) {
					String cellData = getCellData(i,j);
					System.out.print(cellData+" |");
					data[i-1][j] = cellData;
				}
				System.out.println();
			}

			return data;
		}

}
