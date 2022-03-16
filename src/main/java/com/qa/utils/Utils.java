package com.qa.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
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
	//We have to find the common attribute in the all checkboxes in webpage.
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
	
	public int getElementsCount(String tagName) {
		return driver.findElements(By.tagName(tagName)).size();
	}

	public List<String> getAttributesList(String tagName, String attributeName) {

		List<String> attrList = new ArrayList<String>();

		List<WebElement> elementList = driver.findElements(By.tagName(tagName));
		for (WebElement e : elementList) {
			String text = e.getAttribute(attributeName);
			attrList.add(text);
		}

		return attrList;
	}

	public void doClickFromList(By locator, String linkText) {
		List<WebElement> footerList = getElements(locator);

		for (int i = 0; i < footerList.size(); i++) {
			String text = footerList.get(i).getText();
			if (text.equals(linkText)) {
				footerList.get(i).click();
				break;
			}
		}
	}

	//***********Frame Handling methods***************//
	
	public WebDriver switchToFrame(int num) {
		return driver.switchTo().frame(num);
	}
	
	public WebDriver switchToFrame(By locator) {
		return driver.switchTo().frame(getElement(locator));
	}
	
	//************Windows handles *****************//

	public void switchToRightWindow(String windowtitle) {
		Set<String> tabs = driver.getWindowHandles();
		List<String> list = new ArrayList<String>(tabs);
		for (String ls : list) {
			String title = driver.switchTo().window(ls).getTitle();
			if (windowtitle.equals(title)) {
				driver.switchTo().window(windowtitle);
				System.out.println("Found the Right window " + title);
			}
		}

	}

	public void switchToParentWindow() {
		String parentid = driver.getWindowHandle();
		driver.switchTo().window(parentid);
	}

	public void switchToParentWindow(String parentWindow) {
		driver.switchTo().window(parentWindow);
	}

	public void closeAllWindows(List<String> windowList, String parentWindowId) {
		for (String ls : windowList) {
			if (!ls.equals(parentWindowId)) {
				driver.switchTo().window(ls).close();
			}
		}
	}

	public void switchToChildWindow() {
		Set<String> tabs = driver.getWindowHandles();
		Iterator<String> tabSwitch = tabs.iterator();
		String parentid = tabSwitch.next();
		String childid = tabSwitch.next();
		driver.switchTo().window(parentid);
		driver.switchTo().window(childid);
		System.out.println(driver.getTitle());
	}
	
	//***********Drag and drop of items in frame:********//
	
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
	 
		//***********Action Class methods********//
		public void doDoubleClick(By locator) {
			Actions mouse = new Actions(driver);
			mouse.doubleClick(getElement(locator));
		}
	
		public void doContextClick(By locator) {
			Actions mouse = new Actions(driver);
			mouse.doubleClick(getElement(locator));
			//String keys = KeyDOWN(Keys.SHIFT);
		}
		
		public void sendCapitalKeys(String text, By locator) {
			//driver.findElement(locator).sendKeys(KeyDown.);.
			Actions mouse = new Actions(driver);
			mouse.moveToElement(getElement(locator)).keyDown(getElement(locator), Keys.SHIFT).sendKeys(text);
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
	
	//************ Java Alerts *****************//
	
	
	public Alert switchToAlert() {
		try {
		return driver.switchTo().alert();
		}
		catch(NoAlertPresentException e) {
			e.printStackTrace();
			System.out.println("No Alert is present in the webpage");
			return null;
		}
	}
	
	public void switchToAlertAccept() {
		switchToAlert().accept();
	}
	
	public void switchToAlertAccept(String text) {
		String actualText = switchToAlertgetText();
		if(actualText.equals(text))
		switchToAlert().accept();
	}
	
	
	public void switchToAlertDismiss() {
		switchToAlert().dismiss();
	}
	
	public String switchToAlertgetText() {
		return switchToAlert().getText();
	}
	
	public String switchToAlerttoString() {
		return switchToAlert().toString();
	}
	
	
	// ****************take Screenshot methods************************//

	public void takeScreenshotAtEndOfTest() throws IOException {

		TakesScreenshot screenshot = ((TakesScreenshot) driver);
		File scrFile = screenshot.getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}

	/**
	 * This method is used to take the screenshot It will return the path of
	 * screenshot
	 */
	public String getScreenshot() {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
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
