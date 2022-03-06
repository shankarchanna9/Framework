package com.qa.test.resources;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.pages.loginPage;
import com.qa.resources.base;
import com.qa.utils.Utils;

public class loginPageTest extends base {

	

	@BeforeTest
	public void setup() {
		initialization();
	}

	
	@Test
	public void login() {
		// loginPage lp = new loginPage(driver);
		// lp.login();
		//getInstance(loginPage.class).login();
		//
		boolean bool = getInstance(Utils.class).compareTitle("Rahul Shetty Academy- - Login page");
		Assert.assertEquals(bool, true);
		//Assert.assertEquals(getInstance(Utils.class).getTitle(), "Rahul Shetty Academy - Login page");
		System.out.println(getInstance(Utils.class).getTitle());
	}

	@AfterTest
	public void teardown() {
		getInstance(Utils.class).tearDown("quit");
	}

}
