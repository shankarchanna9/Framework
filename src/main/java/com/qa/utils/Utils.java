package com.qa.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.qa.resources.base;

public class Utils extends base {
	
	public WebDriver driver;
	
	public Utils(WebDriver driver) {
		this.driver=driver;
	}

	//************************************* findElements and basic opertions like click, getting size, sendKeys*******************// 
	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}
	public List<WebElement> getElements(By locator) {
		List<WebElement> options = driver.findElements(locator);
		return options;
	}
	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public void doClear(By locator) {
		getElement(locator).clear();		
	}
	public void sendKeys(By locator,String name) {
		getElement(locator).clear();
		getElement(locator).sendKeys(name);
	}
	
	public String getCurrentUrl() {
		String url = driver.getCurrentUrl();
		return url;
	}
	public String getTitle() {
		String title = driver.getTitle();
		return title;
	}
	public int getcheckboxCount(By locator) {
		return getElements(locator).size();
	}
	public int getlinksCount(By locator) {
		return getElements(By.tagName("a")).size();
	}
	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	
	public boolean compareTitle(String titlename) {
		String title = driver.getTitle();
		//Assert.assertEquals(title, titlename);
		if (title.equals(titlename)) return true;
		else return false;
	}
	
	public void choicebyfindelements(By locator, String name) {
		List<WebElement> options = getElements(locator);
		for (WebElement option : options) {
			if (option.getText().equalsIgnoreCase(name)) {
				option.click();
				break;
			}
		}
	}
	
	//***********Drag and drop of items in frame:
	
	public void dragandDrop(By sourceLocator, By DestLocator) {
		Actions action = new Actions(driver);		
		WebElement source = driver.findElement(sourceLocator);
		WebElement target = driver.findElement(DestLocator);
		action.dragAndDrop(source, target).build().perform();
	}
	  
	public void doActionsClick(By locator) { 
		Actions	act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}
	  
	public void doSendKeysWithMoveToElement(By locator, String value) {
		Actions action = new Actions(driver);
		action.moveToElement(getElement(locator)).sendKeys(value).build().perform();
	}
	  
		public void doClickWithMoveToElement(By locator) {
			Actions action = new Actions(driver);
			action.moveToElement(getElement(locator)).click().build().perform();
		}
	 

	
	// ***************************Drop Down Utils***********************************//

	
	public int getdropdownOptionsbySelect(By locator) {
		WebElement element = getElement(locator);
		Select staticdropdown= new Select(element);
		List<WebElement> options = staticdropdown.getOptions();
		return options.size(); 
	}
	
	public void dropdownselectbyIndex(By locator, int index) {
		WebElement element = getElement(locator);
		Select staticdropdown= new Select(element);
		staticdropdown.selectByIndex(index);
	}
	public void dropdownselectbyValue(By locator, String value) {
		WebElement element = getElement(locator);
		Select staticdropdown= new Select(element);
		staticdropdown.selectByValue(value);
	}
	public void dropdownselectbyvisibleText(By locator, String text) {
		WebElement element = getElement(locator);
		Select staticdropdown= new Select(element);
		staticdropdown.selectByVisibleText(text);
	}
	public void selectDropDownValueWithoutSelectClass(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);

		for (WebElement e : optionsList) {

			String text = e.getText();

			if (text.equals(value)) {
				e.click();
				break;
			}

		}
	}
	
	//************************ getting url and navigate methods*******************//
	
	public void getUrl(String url) {
		driver.get(url);
	}
	public void navigateUrl(String url) {
		driver.navigate().to(url);
	}
	public void navigateForward() {
		driver.navigate().forward();
	}
	public void navigateBack() {
		driver.navigate().back();
	}
	public void navigateRefresh() {
		driver.navigate().refresh();
	}
	
	//****************take Screenshot methods************************//
	
public void takeScreenshotAtEndOfTest() throws IOException {
		
		TakesScreenshot screenshot = ((TakesScreenshot) driver);
		File scrFile  = screenshot.getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}
	
	//************************ Teardown methods ******************//
	
	public void tearDown(String method) {
		if(method.equalsIgnoreCase("quit")) {
			driver.quit();
		}
		else if(method.equalsIgnoreCase("close")) {
			driver.close();
		}
		else System.out.println("Give the teardown methods name");
	}
	
}
