package com.qa.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.utils.commonUtils;

public class base {
	public WebDriver driver;
	public Properties prop;
	FileInputStream fis;
	static String browser;
	static String url;

	
	  
	// public base(WebDriver driver){ this.driver=driver; }
	  
	 
	
	public void initialization() {

		prop = new Properties();
		try {
			fis = new FileInputStream(commonUtils.propfilePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("The data properties file or location is invalid");
			e.printStackTrace();
		}
		
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// fetching browsername and url from properties file
		browser = prop.getProperty("browser");
		url = prop.getProperty("URL");

		switch (browser) {

		case "chrome":
			System.setProperty("webdriver.chrome.driver", commonUtils.chromedriverPath);
			driver = new ChromeDriver();// chromedriver instance is created
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", commonUtils.firefoxdriverPath);
			driver = new FirefoxDriver();// firefoxdriver instance is created
			break;

		case "edge":
			System.setProperty("webdriver.gecko.driver", commonUtils.edgedriverPath);
			driver = new EdgeDriver(); // edgedriver instance is created
			break;

		default:
			System.out.println("Please check the browser name");
		}

		// maximizing the browser
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(commonUtils.IMPLICIT_WAIT));
		//return driver;
	}

	// create a method with Java Generics and return a new page
	public <TPage> TPage getInstance(Class<TPage> pageClass) {
		try {
			return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(this.driver);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
