package com.edu.base;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.edu.constant.Constants;
import com.edu.driverConfig.GenericDriver;
import com.edu.utilities.FileUtiliti;

public class BaseSetup extends GenericDriver {
	public static FileUtiliti fileUtiliti = new FileUtiliti().getInstance();
	public static JSONObject UIElement=fileUtiliti.readJsonFile(Constants.UI_ELEMENT_FILE_PATH);
	public static JSONObject testConfig=fileUtiliti.readJsonFile(Constants.TEST_SUITE_FILE_PATH);
	public static SoftAssert softAssert;
	@BeforeSuite
	public void setUp() {
		
		getDriver().manage().window().maximize();
		getDriver().get(String.valueOf(testConfig.get("url")));
		softAssert();
	}

	@AfterSuite
	public void tearDown() {
		softAssert.assertAll();
		getDriver().close();
	}
	
	public SoftAssert softAssert() {
		if(softAssert!=null) {
			return softAssert;
		}else {
			return softAssert=new SoftAssert();
		}
	}

	@Override
	public void dropDownSelect(String size) {
		Select select=new Select(getDriver().findElement(By.id("group_1")));
		select.selectByVisibleText(size);
	}

}
