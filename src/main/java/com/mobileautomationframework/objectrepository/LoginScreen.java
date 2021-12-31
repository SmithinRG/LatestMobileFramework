package com.mobileautomationframework.objectrepository;

import java.time.Duration;
import org.openqa.selenium.support.PageFactory;

import com.mobileautomationframework.base.DriverSetup;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginScreen extends DriverSetup {
	
	
	public LoginScreen(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		
	}

	@AndroidFindBy(id ="search_action_bar_title")
	public AndroidElement searchText;	
	
	@AndroidFindBy(id ="search_action_bar")
	AndroidElement searchButton;
	
	@AndroidFindBy(className ="android.widget.EditText")
	AndroidElement enterText;
	
	//////////////////API DEMOS/////////////////////////
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='App']")
	AndroidElement firstOption;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Search']")
	AndroidElement secondOption;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Invoke Search']")
	AndroidElement thirdOption;
	
	@AndroidFindBy(id ="txt_query_prefill")
	AndroidElement searchTextButton;
	
	
	public void searchClick() {
		searchButton.click();
	}
	public void searchValue(String value) {
		enterText.sendKeys(value);
	}
	
	public AndroidElement searchId() {
		return enterText;
		
	}
	
	public void appClick() {
		firstOption.click();
	}
	
	public void searchOptionClick() {
		secondOption.click();		
	}
	
	public void querySearchClick() {
		thirdOption.click();
	}
	
	public void searchEnterText(String text) {
		searchTextButton.sendKeys(text);	
	}
	
	public AndroidElement searchQuery() {
		return searchTextButton;
		
	}
	
}
