package com.mobileautomationframework.utility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.mobileautomationframework.base.DriverSetup;


public class Listeners extends DriverSetup implements ITestListener{

	ExtentTest test ;
	ExtentReports extent = ExtentReport.createReport();
	
//	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		String testDescription = result.getMethod().getDescription();
		String testMethod = result.getMethod().getMethodName();
		test = extent.createTest(result.getMethod().getDescription());
//		extentTest.set(test);
		test.log(Status.INFO,"Testcase Execution started for - "+testDescription);

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(Status.PASS, "Testcase PASSED");
		try {
			String destination=Utilities.getScreenshot(driver,result.getName());
			test.log(Status.PASS, result.getName());
			test.pass("Screen Shot : " + test.addScreenCaptureFromPath(destination));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		String destination;
		try {
			destination = Utilities.getScreenshot(driver,result.getName());
			test.log(Status.FAIL, result.getName());
			test.log(Status.FAIL,result.getThrowable());
			test.fail("Screen Shot : " + test.addScreenCaptureFromPath(destination));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		String testDescription = result.getMethod().getDescription();
		test.log(Status.INFO,"Test Skipped for - "+testDescription);
		test.log(Status.SKIP, result.getName());
		test.skip("Test Case : " + result.getName() + " has been skipped");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		Utilities.deviceInfo();
		//extent.setSystemInfo("DeviceName", Utilities.deviceName);
		//extent.setSystemInfo("PlatformName", Utilities.brandName);
	//	extent.setSystemInfo("PlatformVersion", Utilities.osVersion);
		extent.flush();
	}
	

}
