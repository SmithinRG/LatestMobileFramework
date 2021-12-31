package com.mobileautomationframework.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.mobileautomationframework.datadriven.DataDriven;
import com.mobileautomationframework.utility.ExtentReport;
import com.mobileautomationframework.utility.Utilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
@Listeners(com.mobileautomationframework.utility.Listeners.class)
public class DriverSetup extends DataDriven  {

	public static AndroidDriver<AndroidElement> driver;
	public static IOSDriver<IOSElement>	driver1;
	public Properties prop;
	public static Map map;
	public static Map mapdata;
	//Utilities generalFunctions = new Utilities();
	//AppiumServer appiumServer = new AppiumServer();
	//ExtentReports extentReports = new ExtentReports();

	@BeforeSuite
	public void reportSetup() {
		ExtentReport.createReport();
	}

	@BeforeTest
	public void CreateDriver() throws MalformedURLException {
		String platform = "android";
		if(platform.equalsIgnoreCase("android")) {
			createAndroidDriver();
		}else {
			createiOSDriver();
		}
	}
	/*public void createAndroidDriver() throws MalformedURLException {
		try {
			getFetchData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("device", "Android");
		cap.setCapability("deviceName", "192.168.100.101:5555");
	//	cap.setCapability("appPackage", "com.android.settings");
	//	cap.setCapability("appActivity", "com.android.settings.Settings");
		cap.setCapability("appPackage", "io.appium.android.apis");
		cap.setCapability("appActivity", "io.appium.android.apis.ApiDemos");
		cap.setCapability("noReset", "true");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),cap);
		System.out.println("BeforeTest");
	}*/
	
	
//BrowserStack
	public void createAndroidDriver() throws MalformedURLException {
		try {
			getFetchData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("browserstack.user", "smithinqa_pjTkxe");
		cap.setCapability("browserstack.key", "qa8fxGHnjQDyMchNzzWk");
	//	cap.setCapability("appPackage", "com.android.settings");
	//	cap.setCapability("appActivity", "com.android.settings.Settings");
		caps.setCapability("app", "bs://5754b71c84ffb58938151c9a4037a9ffcce4fc7f");
		cap.setCapability("appActivity", "io.appium.android.apis.ApiDemos");
		cap.setCapability("noReset", "true");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),cap);
		System.out.println("BeforeTest");
	}	
	public void createiOSDriver() throws MalformedURLException {
		try {
			getFetchData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("device", "iOS");
		cap.setCapability("deviceName","iPhone");
		cap.setCapability("platformVersion", "10.1");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
		//cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath())
		driver1 = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"),cap);

	}





	public void getFetchData() throws Exception {
		String projectPath = System.getProperty("user.dir");
		System.out.println("Test=="+projectPath);
		//		FileInputStream file =new FileInputStream(projectPath+"/src/com/share/utility/Global.properties");
		//	System.out.println("Path"+file);
		prop=new Properties();
		prop.load(new FileInputStream(projectPath+"\\src\\main\\java\\com\\mobileautomationframework\\resources\\Global.properties"));
		//System.out.println(prop.getProperty("TestDataSheet"));
		setExcelFile(prop.getProperty("TestDataPath"), prop.getProperty("TestDataSheetName"));
		map = getCellData();
		setExcelFile(prop.getProperty("TestDataPath"), prop.getProperty("TestDataSheet"));
		mapdata = getCellMapData();
		Map mapDeviceDetail = getDeviceDetail(prop.getProperty("TestDataPath"), "Devicelist");
		//	String strDeviceUDID = mapDeviceDetail.get("GalaxyS9").toString();
	}

	@AfterSuite
	public void reportClose() {

		System.out.println("Test Complete");
		//appiumServer.stopServer();
		//System.out.println("Server Stopped");
	}
}
