package com.edu.driverConfig;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class GenericDriver  {
	WebDriver driver=null;
	public WebDriver getDriver() {
		WebDriverManager.chromedriver().setup();
		if(driver==null) {
			driver=new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	public void sendKeys(String xPath,String data) {
		getDriver().findElement(By.xpath(xPath)).sendKeys(data);
	}
	
	public void waitForPageToLoad(String xPath) {
		try {
			WebDriverWait driverWait=new WebDriverWait(getDriver(), 30);
			driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void clickByXpath(String xPath) {
		getDriver().findElement(By.xpath(xPath)).click();
	}
	
	public abstract void dropDownSelect(String size);
	
	public boolean isElementPresent(String xPtah) {
		WebElement element=getDriver().findElement(By.xpath(xPtah));
		return element.isDisplayed();
	}
	
	public boolean isElementSelected(String xPath) {
		WebElement element=getDriver().findElement(By.xpath(xPath));
		return element.isSelected();
	}
	
	public boolean verifyInputTextWithUI(String xPath,String inputData) {
		WebElement element=getDriver().findElement(By.xpath(xPath));
		String elementText=element.getText();
		if(elementText.contains(inputData)) {
			return true;
		}
		return false;
	}
	public void clikUsingActionClass(String xPath1, String xPath2 ) {
		Actions action = new Actions(getDriver());
		System.out.println("xPath"+xPath1);
		WebElement element =getDriver().findElement(By.xpath(xPath2));
		action.moveToElement(element).perform();
		WebElement element1 =getDriver().findElement(By.xpath(xPath1));
		action.moveToElement(element1).click().perform();
	}
	public boolean priceCheck(String fromUI, String fromSetUp) {
		WebElement element=getDriver().findElement(By.xpath(fromUI));
		return Double.parseDouble(element.getText().replace("$", "").trim())>Double.parseDouble(fromSetUp.replace("$", "").trim());
		//return false;
	}
}
