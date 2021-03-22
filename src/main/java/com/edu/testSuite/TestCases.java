package com.edu.testSuite;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.edu.base.BaseSetup;
import com.edu.utilities.FileUtiliti;

public class TestCases extends BaseSetup{
	@Test
	public void userLogin() {
			waitForPageToLoad(String.valueOf(UIElement.get("SIGN_IN_BTN")));
			clickByXpath(String.valueOf(UIElement.get("SIGN_IN_BTN")));
			waitForPageToLoad(String.valueOf(UIElement.get("EMAIL_FIELD")));
			sendKeys(String.valueOf(UIElement.get("EMAIL_FIELD")),String.valueOf(testConfig.get("userName")));
			sendKeys(String.valueOf(UIElement.get("PASSWORD_FIELD")),String.valueOf(testConfig.get("password")));
			clickByXpath(String.valueOf(UIElement.get("LOGIN_BTN")));
			waitForPageToLoad(String.valueOf(UIElement.get("SIGNED_OUT_BTN")));
			String logOutText=getDriver().findElement(By.xpath(String.valueOf(UIElement.get("SIGNED_OUT_BTN")))).getText();
			System.out.println(logOutText);
			softAssert.assertEquals("logOutText", "Sign out");
	}
	
	@Test(dependsOnMethods = "userLogin",dataProvider = "iteamDetails",dataProviderClass = FileUtiliti.class)
	public void purchaseDress(String clothesType,String maxPrice,String size,String colour) {
		clickByXpath(String.valueOf(UIElement.get("HOME_LINK")));
		waitForPageToLoad(String.valueOf(UIElement.get("SEARCH_BOX")));
		sendKeys(String.valueOf(UIElement.get("SEARCH_BOX")), clothesType);
		clickByXpath(String.valueOf(UIElement.get("SEARCH_BTN")));
		List<WebElement> elementList=getDriver().findElements(By.xpath(String.valueOf(UIElement.get("SELECT_ITEM"))));
		System.out.println("elementList.size()"+elementList.size());
		for(int i=1;i<=elementList.size();i++) {
			String itemXpath=String.valueOf(UIElement.get("SELECT_ITEM")).concat("[").concat(String.valueOf(i)).concat("]");
			String itemXpath2=String.valueOf(UIElement.get("ITEM_FILTER")).concat("[").concat(String.valueOf(i)).concat("]");
			clikUsingActionClass(itemXpath,itemXpath2);
			waitForPageToLoad(String.valueOf(UIElement.get("ITEM_DESCRIPTION")));
			WebElement element=getDriver().findElement(By.xpath(String.valueOf(UIElement.get("ITEM_DESCRIPTION"))));
			if(element.getText().contains("100% cotton")) {
				softAssert.assertEquals(priceCheck(String.valueOf(UIElement.get("ITEM_PRICE")), maxPrice), true);
				dropDownSelect(size.split("-")[1]);
				clickByXpath(String.valueOf(UIElement.get("ADD_TO_CARD_BTN")));
			}
			clickByXpath(String.valueOf(UIElement.get("BACK_TO_ITEMS")));
		}
	}
}
