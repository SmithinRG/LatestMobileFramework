package com.mobileautomationframework.utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableMap;
import com.mobileautomationframework.base.DriverSetup;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

import java.util.List;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import io.appium.java_client.MobileBy;

public class Utilities extends DriverSetup {
	
	public static String brandName;
	public static String deviceName;
	public static String osVersion;
	public static String buildVersion;

	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
        //below line is just to append the date format with the screenshot name to avoid duplicate names		
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        System.out.println("Screenshot Capture");
        //Returns the captured file path
        return destination;
}
	
	public static void deviceInfo() {

		Map<Object, Object> deviceDetails =  (Map<Object, Object>) driver.executeScript("mobile: deviceInfo");
		System.out.println("TT"+deviceDetails);
		//brandName = deviceDetails.get("model").toString();
		//deviceName = deviceDetails.get("name").toString();
		//osVersion = deviceDetails.get("platformVersion").toString();
		//	buildVersion = driver.getStatus();
		System.out.println("build"+driver.getStatus());

	}

	public void ClickSignIn()
	{
		MobileElement element = driver.findElementById("com.maf.sharesit:id/onboarding_already_have_account_view");
		Point p = element.getCenter();
		int Xcord =p.getX();
		int Ycord = p.getY();
		/*
		Point objPont =element.getLocation();
		int Xcord =objPont.getX();
		int Ycord = objPont.getY();
		 */
		System.out.println("Cords "+Xcord+","+Ycord);
		TouchAction action= new TouchAction(driver).tap(point(Xcord+200,Ycord)).waitAction(waitOptions(Duration.ofMillis(1000))).perform();

	}

	public String getText(IOSElement element)
	{
		String text = element.getText();
		return text;
	}


	public void swipeLeft()
	{
		new TouchAction(driver).
		longPress(point(200, 180)).moveTo(point(790, 180)).release().perform();

	}


	public void swipeHorizontal( double startPercentage, double finalPercentage, double anchorPercentage) throws Exception
	{
		Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.height * anchorPercentage);
		int startPoint = (int) (size.width * startPercentage);
		int endPoint = (int) (size.width * finalPercentage);

		new TouchAction(driver).press(PointOption.point(startPoint, anchor)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(endPoint, anchor)).release().perform();

	}


	public void SimplyScrollDown() throws Exception
	{

		new TouchAction(driver).press(PointOption.point(550, 940)).waitAction().moveTo(PointOption.point(550, 60)).release().perform();

	}


	public int elementCount(String elementXapath)
	{
		int size=0;

		List optionCount =driver.findElementsByXPath(elementXapath);

		size = optionCount.size();

		return size;


	}

	public void pressEnter()
	{
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).perform();
	}

	public void pressSearch()
	{

		driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));

	}



	public boolean MoveToElement(String Xpath) throws Exception
	{

		AndroidElement element = null;
		Dimension size = driver.manage().window().getSize();

		//x position set to mid-screen horizontally
		int width = size.width / 2;

		//Starting y location set to 80% of the height (near bottom)
		int startPoint = (int) (size.getHeight() * 0.80);

		//Ending y location set to 20% of the height (near top)
		int endPoint = (int) (size.getHeight() * 0.20);

		boolean existFlag = true;

		for (int i = 0; i < 20; i++)
		{

			try
			{

				new TouchAction(driver).press(PointOption.point(width, startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(width, endPoint)).release().perform();

				element = driver.findElementByXPath(Xpath);
				existFlag=true;
				break;


			}
			catch (Exception ex)
			{
				System.out.println(String.format("Element not available. Scrolling (%s) timesï¿½", i + 1));
				existFlag=false;
			}
		}

		if(existFlag==false)
		{
			Assert.fail("Failed to Find the Element");
		}

		element.click();
		Thread.sleep(2000);

		if(element.isEnabled()==true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	public void RandomlyScrollToElement(String Xpath) throws Exception
	{
		AndroidElement element = null;

		Random rand = new Random();

		int rand_int = rand.nextInt(5);
		System.out.println("Random Number for swipe:  "+rand_int);

		Dimension size = driver.manage().window().getSize();
		int anchor = size.width / 2;
		//Swipe up to scroll down
		int startPoint = size.height - 5;
		int endPoint = 300;

		System.out.println("point "+startPoint+","+endPoint);

		for (int i = 0; i < rand_int; i++)
		{

			new TouchAction(driver)
			.longPress(point(anchor, startPoint))
			.moveTo(point(anchor, endPoint)).release().perform();
			//element = (AndroidElement) driver.findElementByXPath(Xpath);

			System.out.println(String.format("Scrolling (%s) timeï¿½", i));
			Thread.sleep(2000);
		}

		try
		{
			element = driver.findElementByXPath(Xpath);
		}
		catch(Exception x)
		{
			Assert.fail("Failed to Find the Element");
		}
		element.click();

	}


	public int  GenerateRandomNumber(int number)
	{

		Random rand = new Random();

		int rand_int = rand.nextInt(number);
		System.out.println("Random Number Generated:  "+rand_int);
		return rand_int;
	}



	/**
	 * Wait for element to appear on the screen
	 * @param driver
	 * @param locatorObject
	 */
	public void waitForElementToBeVisible(WebDriver driver, By locatorObject) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locatorObject));
	}

	public boolean isElementVisible(WebElement element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;	
		}catch(Exception e) {
			return false;
		}
	}

	/**
	 * Waits for element to clickable
	 * @param WebElement
	 */
	public boolean isElementToBeClickable(WebElement element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean isElementInvisible(WebElement element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.invisibilityOf(element));
			return true;
		}catch(Exception e){
			return false;
		}

	}

	public boolean isElementToBeSelected(WebElement element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.elementToBeSelected(element));
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public void keyBoardSuggDone() {
		driver.findElementByXPath("//*[@name='Done']").click();
	}
	public void keyBoardSuggNext() {
		driver.findElementByXPath("//*[@name='Next']").click();
	}

	/*	protected MobileElement scrollToElementByTextContains(String text) {
	    driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\" + text + "\").instance(0));
	}
	 */
	public boolean isElementPresent(MobileElement elementName, int timeout){
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOf(elementName));
			return true;
		}catch(Exception e){
			return false;
		}
	}
	//Call this function for Capture toast

	public boolean isElementPresence(By elementName, int timeout){
		try{
			WebDriverWait waitForToast = new WebDriverWait(driver, timeout);
			waitForToast.until(ExpectedConditions.presenceOfElementLocated(elementName));
			//			String toastMessage = driver.findElement(elementName).getText();
			//			System.out.println(toastMessage);
			return true;
		}catch(Exception e){
			return false;
		}
	}


	//************************************************Toast
	public String toastMessages() {
		WebDriverWait waitForToast = new WebDriverWait(driver,25);

		waitForToast.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/hierarchy/android.widget.Toast")));

		String toastMessage = driver.findElement((By.xpath("/hierarchy/android.widget.Toast"))).getText();
		System.out.println(toastMessage);
		return toastMessage ;
	}



	public void rightLeftSwipe() throws Exception {
		Thread.sleep(500);
		Dimension size = driver.manage().window().getSize();
		System.out.println(size);
		int endx = (int) (size.width * 0.8);
		System.out.println(endx);
		int startx = (int) (size.width * 0.20);
		System.out.println(startx);
		int starty = size.height / 2;
		System.out.println(starty);
		for(int i=0;i<=3;i++) {
			//   appiumDriver..swipe(startx, starty, endx, starty, 1000);
			new TouchAction(driver).press(PointOption.point(startx, starty)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(endx, starty)).release().perform();

		}
	}
	//SWIPE GENERIC

	public void swipeScreen(Directions dir) {
		//  System.out.println("swipeScreen(): dir: '" + dir + "'"); // always log your actions

		// Animation default time:
		//  - Android: 300 ms
		//  - iOS: 200 ms
		// final value depends on your app and could be greater
		final int ANIMATION_TIME = 200; // ms

		final int PRESS_TIME = 200; // ms

		int edgeBorder = 10; // better avoid edges
		PointOption pointOptionStart, pointOptionEnd;
		//  System.out.println("STart----");
		// init screen variables
		Dimension dims = driver.manage().window().getSize();
		//System.out.println("Di----"+dims);
		// init start point = center of screen
		pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
		//System.out.println("Point=====----"+pointOptionStart);
		//System.out.println("Switch=====----");
		switch (dir) {

		case DOWN: // center of footer
			pointOptionEnd = PointOption.point(dims.width / 2, dims.height - edgeBorder);
			break;
		case UP: // center of header
			pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
			break;
		case LEFT: // center of left side
			pointOptionEnd = PointOption.point(edgeBorder, dims.height / 2);
			break;
		case RIGHT: // center of right side
			pointOptionEnd = PointOption.point(dims.width - edgeBorder, dims.height / 2);
			break;
		default:
			throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
		}
		System.out.println("Switch end=====----");
		// execute swipe using TouchAction
		try {
			new TouchAction(driver)
			.press(pointOptionStart)
			// a bit more reliable when we add small wait
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
			.moveTo(pointOptionEnd)
			.release().perform();
		} catch (Exception e) {
			System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
			return;
		}
		System.out.println("Switch swipe=====----");
		// always allow swipe action to complete
		try {
			Thread.sleep(ANIMATION_TIME);
		} catch (InterruptedException e) {
			// ignore
		}
	}

	public enum Directions {
		UP,
		DOWN,
		LEFT,
		RIGHT;
	}
	//SCROLL_TO_TEXT

	public void mobileSwipeScreenIOS(Directions dir) {
		System.out.println("mobileSwipeScreenIOS(): dir: '" + dir + "'"); // always log your actions

		// Animation default time:
		//  - iOS: 200 ms
		// final value depends on your app and could be greater
		final int ANIMATION_TIME = 200; // ms
		final HashMap<String, String> scrollObject = new HashMap<String, String>();

		switch (dir) {
		case DOWN: // from up to down (! differs from mobile:scroll)
			scrollObject.put("direction", "down");
			break;
		case UP: // from down to up  (! differs from mobile:scroll)
			scrollObject.put("direction", "up");
			break;
		case LEFT: // from right to left  (! differs from mobile:scroll)
			scrollObject.put("direction", "left");
			break;
		case RIGHT: // from left to right  (! differs from mobile:scroll)
			scrollObject.put("direction", "right");
			break;
		default:
			throw new IllegalArgumentException("mobileSwipeScreenIOS(): dir: '" + dir + "' NOT supported");
		}
		//  scrollObject.put("element", el.getText());
		try {
			driver.executeScript("mobile:swipe", scrollObject);
			Thread.sleep(ANIMATION_TIME); // always allow swipe action to complete
		} catch (Exception e) {
			System.err.println("mobileSwipeScreenIOS(): FAILED\n" + e.getMessage());
			return;
		}
	}
	

	

	public boolean viewComparison(String view) {	
		String WebViewEl = driver.getContextHandles().toString();	
		if(WebViewEl.equals(view)) {	
			return true;	
		}else {	
			return false;	
		}	
	}

	public void keyboardDone()	
	{	

		driver.findElementByName("Done").click(); 	
	}	

	public void keyboardGo()	
	{	

		driver.findElementByName("go").click(); 	
	}

	
	public void iOSScrollDown(String text)
	{
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			HashMap scrollObject = new HashMap<>();
			scrollObject.put("predicateString", "value == '" + text + "'");
			scrollObject.put("direction", "down");
			js.executeScript("mobile: scroll", scrollObject);
		}
		catch(Exception e)
		{
			Assert.fail(e.getMessage());
		}

	}
	//left to right
	public void leftRightSwipe() throws Exception {
		Thread.sleep(1000);
		Dimension size = driver.manage().window().getSize();
		System.out.println(size);
		int startx = (int) (size.width * 0.8);
		System.out.println(startx);
		int endx = (int) (size.width * 0.20);
		System.out.println(endx);
		int starty = size.height / 2;
		System.out.println(starty);
		for(int i=0;i<=3;i++) {
			new TouchAction(driver).press(PointOption.point(startx, starty)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(endx, startx)).release().perform();

		}
	}
}