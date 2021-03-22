package com.edu.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;

import com.edu.constant.Constants;

public class FileUtiliti {
	private static FileUtiliti single_instance = null;
	static Workbook book;
	static Sheet sheet;
	public FileUtiliti getInstance() {
		if (single_instance == null)
			single_instance = new FileUtiliti();
		return single_instance;
	}

	public JSONObject readJsonFile(String filePath) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		Object object = null;
		try {
			FileReader fileReader = new FileReader(filePath);
			object = jsonParser.parse(fileReader);
			jsonObject = (JSONObject) object;
			jsonObject.get("SIGN_IN_BTN");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	@DataProvider(name = "iteamDetails")
	public Object[][] readExcelFile() {
		FileInputStream file=null;
		Object[][]data=null;
		try {
			file=new FileInputStream(Constants.TEST_DATA_FILE_PATH);
		}catch(FileNotFoundException e) {
			e.getSuppressed();
		}
		try {
			book=WorkbookFactory.create(file);
			sheet=book.getSheet("testData");
			data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			for(int i=0;i<sheet.getLastRowNum();i++) {
				for(int j=0;j<sheet.getRow(0).getLastCellNum();j++) {
					data[i][j]=sheet.getRow(i+1).getCell(j).toString();
				}
			}
		} catch (InvalidFormatException  e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
