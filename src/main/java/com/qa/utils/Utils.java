package com.qa.utils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
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

	public void switchToChildWindow(String childWindow) {
		driver.switchTo().window(childWindow);
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
	
	//**************WebDriver Scope Limiting*************//
	
	
		/*Steps:	1. To finds number of links in webpage, simply we can find by 
		 * 				findElements method using tagName with "a"
				2. To find a number of links in particular position in webpage, 
					we have limit the scope by selecting the portion by findElement and 
					apply tagname count to that limited_driver			
		*/
	
		public WebElement webdriverScopeLimit(By locator) {
			WebElement driverlimit = getElement(locator);
			return driverlimit;
		}
		
		public int linksCountWebDriverScopeLimit(By locator) {
			WebElement loca=webdriverScopeLimit(locator);
			int links = loca.findElements(By.tagName("a")).size();
			return links;
		}
		
	
	
	
	//******************Drag and drop of items in frame:***************//
	
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
	//************** SSL Certificates and Insecure page acceptance method*****************//
	
	public DesiredCapabilities SSLCertificateAccept() {
		DesiredCapabilities ch = new DesiredCapabilities();
		ch.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		// ch.acceptInsecureCerts();
		ch.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return ch;

		//		ChromeOptions c= new ChromeOptions();
		//		c.merge(ch);
		//		String path = "C:\\Users\\System 1\\Documents\\Selenium_Traning\\chromedriver.exe";
		//		System.setProperty("webdriver.chrome.driver", path);
		//		WebDriver driver = new ChromeDriver(c);

	}
	
	//******** Cookies deleting ********//
	
	public void allCookiesDelete() {
		driver.manage().deleteAllCookies();
	}
	
	public void namedCookieDelete(String cookieName) {
		driver.manage().deleteCookieNamed(cookieName);
	}
	
	public void maximizeWindow() {
		driver.manage().window().maximize();
	}
	
	public void minimizeWindow() {
		driver.manage().window().minimize();
	}
	
	// ****************take Screenshot methods************************//

	public void takeScreenshot() throws IOException {

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

	public void takeScreenshot(By locator) {

		WebElement partialScreenshot = getElement(locator);
		File scrFile = partialScreenshot.getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		try {
			FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//**************Broken Link ********************//
	
	public boolean verifyBrokenLink(String link) throws IOException {
		HttpURLConnection conn = (HttpURLConnection)new URL(link).openConnection();
		conn.setRequestMethod("HEAD");
		conn.connect();
		int respCode = conn.getResponseCode();
		if(respCode>=400) {
			return true;
		}
		return false;
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
