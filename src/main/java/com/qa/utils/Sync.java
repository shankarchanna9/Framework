package com.qa.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.constants.commonUtils;
import com.qa.resources.base;

public class Sync extends base{

	public WebDriver driver;
	
	public Sync(WebDriver driver) {
		this.driver=driver;
	}
	
	public void implicitWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));		
	}
	public void implicitWait() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(commonUtils.IMPLICIT_WAIT));		
	}
	

	public void elementToBeClickableWait(By locator) {
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(commonUtils.EXPLICIT_WAIT));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	public void elementToBeClickableWait(By locator,int duration) {
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(duration));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	// ***************************** wait utils ************************

			public List<WebElement> visibilityOfAllElements(By locator, int timeOut) {
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
				return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			}

			public void getPageLinksText(By locator, int timeOut) {
				visibilityOfAllElements(locator, timeOut).stream().forEach(ele -> System.out.println(ele.getText()));
			}

			public int getPageLinksCount(By locator, int timeOut) {
				return visibilityOfAllElements(locator, timeOut).size();
			}

			public String waitForTitlePresent(String titleValue, int timeOut) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
				wait.until(ExpectedConditions.titleIs(titleValue));
				return driver.getTitle();
			}

				public Alert waitForAlertToBePresent(int timeOut) {
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
				return wait.until(ExpectedConditions.alertIsPresent());
			}

			public boolean waitForUrl(String urlValue, int timeOut) {
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
				return wait.until(ExpectedConditions.urlContains(urlValue));
			}

			public WebElement waitForElementToBeLocated(By locator, int timeOut) {
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
				return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			}

			public WebElement waitForElementToBeVisible(By locator, int timeOut) {
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
				return wait.until(ExpectedConditions.visibilityOf(getInstance(Utils.class).getElement(locator)));
			}

			public void clickWhenReady(By locator, int timeOut) {
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
				wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
			}

			public WebElement waitForElementWithFluentWait(By locator, int timeOut, int pollingTime) {
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
						.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
						.ignoring(StaleElementReferenceException.class);

				return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			}

			/**
			 * This is custom dynamic wait to find the webelement
			 * 
			 * @param locator
			 * @return
			 */
		
		public WebElement retryingElement(By locator) {
			WebElement element = null;
			int attempts = 0;

			while (attempts < 30) {
				try {
					element = driver.findElement(locator);
					break;
				}
				catch (org.openqa.selenium.StaleElementReferenceException e) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
					}
				}
				catch (org.openqa.selenium.NoSuchElementException e) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
					}
					System.out.println("element is not found in attempt : " + (attempts + 1));
				}
				attempts++;
			}
			return element;
		}
	
	
	
}
