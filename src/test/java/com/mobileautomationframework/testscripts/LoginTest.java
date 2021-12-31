package com.mobileautomationframework.testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mobileautomationframework.base.DriverSetup;
import com.mobileautomationframework.functions.LoginFunctions;
import com.mobileautomationframework.objectrepository.LoginScreen;

public class LoginTest extends DriverSetup {
	
/*	@Test(description = "The First Test")
	public void settingsTest() throws InterruptedException {
		LoginScreen loginScreen = new LoginScreen(driver);
		LoginFunctions loginFunctions = new LoginFunctions();
		System.out.println("Test Success");
	Assert.assertTrue(loginScreen.searchText.isDisplayed(), "Button is not visible");
	loginFunctions.searchValue(loginScreen);
	//Assert.assertEquals(loginScreen.searchId().getText(), "Smith");
	Assert.assertEquals(loginScreen.searchId().getText(), "Sm", "The entered text is NOT correct");
	
	}
*/
	@Test(description = "Cloud Device Testing")
	public void appDemosTest() {
		LoginScreen loginScreen = new LoginScreen(driver);
		LoginFunctions loginFunctions = new LoginFunctions();
		loginFunctions.searchText(loginScreen);
		Assert.assertEquals(loginScreen.searchQuery().getText(), "Sm");
	}
}
