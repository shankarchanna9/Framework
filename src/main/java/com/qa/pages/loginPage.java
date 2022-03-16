package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.resources.base;
import com.qa.utils.Utils;

public class loginPage extends base{

	public WebDriver driver;
//	Utils ut;
	By username= By.cssSelector("input[id='inputUsername']");
	By password= By.cssSelector("input[name='inputPassword']");
	By remCheckbox= By.cssSelector("input[id='chkboxOne']");
	By policyCheckbox= By.cssSelector("input[id='chkboxTwo']");
	By loginbtn = By.cssSelector("button.submit.signInBtn");
	By alertbtnURL2 = By.id("alertbtn");
	
	public loginPage(WebDriver driver){
		this.driver=driver;
	}
	
	
	
	public WebElement getUsername() {
		return driver.findElement(username);
	}
	public WebElement getPassword() {
		return driver.findElement(password);
	}
	public void login() {
		Utils ut= new Utils(driver);
		ut.sendKeys(username, "rahul@gmail.com");
		ut.sendKeys(password, "rahul");
		ut.doClick(loginbtn);		
	}
	public String alert() {
		Utils ut= new Utils(driver);
		ut.doClick(alertbtnURL2);
		return ut.switchToAlertgetText();
	}
	public String alert2() {
		Utils ut= new Utils(driver);
		ut.doClick(alertbtnURL2);
		return ut.switchToAlerttoString();
	}
	
	
}
