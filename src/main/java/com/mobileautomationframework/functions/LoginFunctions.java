package com.mobileautomationframework.functions;

import com.mobileautomationframework.base.DriverSetup;
import com.mobileautomationframework.objectrepository.LoginScreen;

public class LoginFunctions extends DriverSetup {
	
	String searchName = map.get("UserName").toString();
	
	public void searchValue(LoginScreen loginScreen) throws InterruptedException {
		loginScreen.searchClick();
		Thread.sleep(500);
		loginScreen.searchValue(searchName);
		
	}
	
public void searchText(LoginScreen loginScreen) {
	loginScreen.appClick();
	loginScreen.searchOptionClick();
	loginScreen.querySearchClick();
	loginScreen.searchEnterText(searchName);
}
}
